package com.recody.recodybackend.drama.features.initializedramas;

import com.recody.recodybackend.drama.data.drama.DramaEntity;
import com.recody.recodybackend.drama.features.discoverdramafromtmdb.DiscoverDramaFromTMDB;
import com.recody.recodybackend.drama.features.discoverdramafromtmdb.DiscoverDramaFromTMDBHandler;
import com.recody.recodybackend.drama.features.registerdrama.RegisterDrama;
import com.recody.recodybackend.drama.features.registerdrama.RegisterDramaHandler;
import com.recody.recodybackend.drama.tmdb.TMDBDrama;
import com.recody.recodybackend.drama.tmdb.TMDBSearchDramaResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Locale;

@Component
@RequiredArgsConstructor
@Slf4j
class DefaultInitializeDramasHandler implements InitializeDramasHandler<Void>{

    private final DiscoverDramaFromTMDBHandler<TMDBSearchDramaResponse> discoverDramaFromTMDBHandler;
    private final RegisterDramaHandler<DramaEntity> registerDramaHandler;

    private Integer totalPages;

    @Override
    public Void handle() {
        log.debug( "initialize dramas");


        TMDBSearchDramaResponse dramaResponse=discoverDramaFromTMDBHandler.handle(
                DiscoverDramaFromTMDB.builder()
                        .language(Locale.KOREAN.getLanguage())
                        .page(1)
                        .build() );

        totalPages = dramaResponse.getTotalPages();

        for (Integer page=1;page<=totalPages;page++) {
            log.debug( "initial dramas for page: {}, total page : {}", page, totalPages );

            TMDBSearchDramaResponse response=discoverDramaFromTMDBHandler.handle(
                    DiscoverDramaFromTMDB.builder()
                            .language(Locale.KOREAN.getLanguage())
                            .page(page)
                            .build() );
            List<TMDBDrama> dramas = response.getResults();
            for (TMDBDrama drama : dramas) {
                registerDramaHandler.handle( RegisterDrama.builder()
                        .drama( drama )
                        .locale( Locale.KOREAN ).build() );
            }
        }



        return null;
    }
}
