package com.recody.recodybackend.drama.features.synchronizedramaseachlanguages;

import com.recody.recodybackend.drama.SearchingKeyword;
import com.recody.recodybackend.drama.data.drama.DramaEntity;
import com.recody.recodybackend.drama.data.drama.DramaMapper;
import com.recody.recodybackend.drama.data.drama.DramaRepository;
import com.recody.recodybackend.drama.features.searchdramafromtmdb.SearchDramaFromTMDB;
import com.recody.recodybackend.drama.features.searchdramafromtmdb.SearchDramaFromTMDBHandler;
import com.recody.recodybackend.drama.tmdb.TMDBDrama;
import com.recody.recodybackend.drama.tmdb.TMDBSearchDramaResponse;
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
class DefaultSynchronizeDramasEachLanguagesHandler implements SynchronizeDramasEachLanguagesHandler<Void>{
    
    private final SearchDramaFromTMDBHandler<TMDBSearchDramaResponse> searchDramaFromTMDBHandler;
    private final DramaMapper dramaMapper;
    private final DramaRepository dramaRepository;
    /**
     * 현재 지원하는 언어는 한국어, 영어.
     */
    private static final List<Locale> locales = List.of( Locale.KOREAN, Locale.ENGLISH );
    
    @Override
    @Transactional
    public Void handle(SearchingKeyword keyword) {
        log.debug( "synchronizing dramas for keyword: {}", keyword );
    
        for (Locale locale : locales) {
            TMDBSearchDramaResponse response = searchDramaFromTMDBHandler.handle(
                    SearchDramaFromTMDB.builder()
                                       .queryText( keyword.getValue() )
                                       .language( locale.getLanguage() )
                                       .build() );
    
            List<TMDBDrama> dramas = response.getResults();
            for (TMDBDrama drama : dramas) {
                // tmdb id 로 이미 있는지 확인한다.
                Optional<DramaEntity> optionalDrama = dramaRepository.findByTmdbId( drama.getId() );
                // entity 로 데이터를 저장한다.
                if ( optionalDrama.isEmpty() ) {
                    DramaEntity dramaEntity = dramaMapper.newEntity( drama, locale );
                    dramaRepository.save( dramaEntity );
                }
                else {
                    dramaMapper.update( optionalDrama.get(), drama, locale );
                }
            }
        }
        
        return null;
    }
}
