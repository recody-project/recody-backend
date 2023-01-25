package com.recody.recodybackend.drama.features.synchronizedramaseachlanguages;

import com.recody.recodybackend.drama.SearchingKeyword;
import com.recody.recodybackend.drama.data.drama.DramaEntity;
import com.recody.recodybackend.drama.features.registerdrama.RegisterDrama;
import com.recody.recodybackend.drama.features.registerdrama.RegisterDramaHandler;
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

@Component
@RequiredArgsConstructor
@Slf4j
class DefaultSynchronizeDramasEachLanguagesHandler implements SynchronizeDramasEachLanguagesHandler<Void> {
    
    /**
     * 현재 지원하는 언어는 한국어, 영어.
     */
    private static final List<Locale> locales = List.of( Locale.KOREAN, Locale.ENGLISH );
    private final SearchDramaFromTMDBHandler<TMDBSearchDramaResponse> searchDramaFromTMDBHandler;
    private final RegisterDramaHandler<DramaEntity> registerDramaHandler;
    
    
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
                registerDramaHandler.handle( RegisterDrama.builder()
                                                     .drama( drama )
                                                     .locale( locale ).build() );
            }
        }
        
        return null;
    }
}
