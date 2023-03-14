package com.recody.recodybackend.drama.features.registerdrama;

import com.recody.recodybackend.common.data.AsyncLinkingEntityManager;
import com.recody.recodybackend.drama.data.drama.DramaEntity;
import com.recody.recodybackend.drama.data.drama.DramaMapper;
import com.recody.recodybackend.drama.data.drama.DramaRepository;
import com.recody.recodybackend.drama.data.genre.DramaGenreCodeEntity;
import com.recody.recodybackend.drama.data.genre.DramaGenreCodeRepository;
import com.recody.recodybackend.drama.data.genre.DramaGenreEntity;
import com.recody.recodybackend.drama.tmdb.TMDBDrama;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
class DefaultRegisterDramaHandler implements RegisterDramaHandler<DramaEntity> {
    
    private final DramaMapper dramaMapper;
    private final DramaRepository dramaRepository;
    
    private final DramaGenreCodeRepository genreCodeRepository;
    
    private final AsyncLinkingEntityManager<DramaGenreEntity, DramaEntity, DramaGenreCodeEntity> genreRegistrar;
    
    @Override
    public DramaEntity handle(RegisterDrama command) {
        log.trace( "handling command: {}", command );
        // tmdb id 로 이미 있는지 확인한다.
        TMDBDrama drama = command.getDrama();
        Locale locale = command.getLocale();
        Optional<DramaEntity> optionalDrama = dramaRepository.findByTmdbId( drama.getId() );
        // entity 로 데이터를 저장한다.
        DramaEntity dramaEntity;
    
        if ( optionalDrama.isEmpty() ) {
            DramaEntity newEntity = dramaMapper.newEntity( drama, locale );
            dramaEntity = dramaRepository.save( newEntity );
        }
        else {
            dramaEntity = optionalDrama.get();
            dramaMapper.update( dramaEntity, drama, locale );
        }
    
        // 장르 정보 저장
        List<Integer> tmdbGenreIds = drama.getGenreIds();
        genreCodeRepository.findByTmdbGenreIdIn( tmdbGenreIds )
                           .thenApply( genreIds -> genreRegistrar.save( dramaEntity, genreIds ) );
        return dramaEntity;
    }
}
