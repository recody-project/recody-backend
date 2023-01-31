package com.recody.recodybackend.drama.features.initializedramagenres;

import com.recody.recodybackend.drama.data.genre.DramaGenreCodeEntity;
import com.recody.recodybackend.drama.data.genre.DramaGenreCodeRepository;
import com.recody.recodybackend.drama.data.genre.DramaGenreMapper;
import com.recody.recodybackend.drama.features.fetchdramagenres.FetchDramaGenres;
import com.recody.recodybackend.drama.features.fetchdramagenres.FetchDramaGenresHandler;
import com.recody.recodybackend.drama.tmdb.genre.TMDBDramaGenre;
import com.recody.recodybackend.drama.tmdb.genre.TMDBDramaGenres;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
class DefaultInitializeDramaGenresHandler implements InitializeDramaGenresHandler<Void> {
    
    private final FetchDramaGenresHandler<TMDBDramaGenres> fetchDramaGenresHandler;
    
    private final DramaGenreMapper dramaGenreMapper;
    
    private final DramaGenreCodeRepository genreCodeRepository;
    
    public static final List<Locale> locales = List.of( Locale.KOREAN, Locale.ENGLISH );
    
    
    @Override
    @Transactional
    public Void handle() {
        log.trace( "Initializing DramaGenres" );
        for (Locale locale : locales) {
            TMDBDramaGenres response = fetchDramaGenresHandler.handle( FetchDramaGenres.builder()
                                                                                       .locale( locale )
                                                                                       .build() );
            List<TMDBDramaGenre> tmdbGenres = response.getValues();
            for (TMDBDramaGenre tmdbGenre : tmdbGenres) {
                Optional<DramaGenreCodeEntity> optionalEntity = genreCodeRepository.findByTmdbGenreId( tmdbGenre.getId() );
                if (optionalEntity.isPresent()){
                    log.trace( "존재하는 장르 엔티티에 장르 이름을 업데이트" );
                    DramaGenreCodeEntity dramaGenreCodeEntity = optionalEntity.get();
                    dramaGenreMapper.updateName( dramaGenreCodeEntity, tmdbGenre, locale );
                }
                else {
                    log.trace( "새로운 장르 엔티티 저장" );
                    DramaGenreCodeEntity dramaGenreCodeEntity =
                            dramaGenreMapper.newEntity( tmdbGenre, locale );
                    genreCodeRepository.save( dramaGenreCodeEntity );
                }
            }
        }
        
        return null;
    }
}
