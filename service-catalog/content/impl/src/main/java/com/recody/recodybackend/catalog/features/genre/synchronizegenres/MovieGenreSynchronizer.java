package com.recody.recodybackend.catalog.features.genre.synchronizegenres;

import com.recody.recodybackend.catalog.data.genre.CatalogGenreEntity;
import com.recody.recodybackend.catalog.data.genre.CatalogGenreMapper;
import com.recody.recodybackend.catalog.data.genre.CatalogGenreRepository;
import com.recody.recodybackend.movie.MovieGenre;
import com.recody.recodybackend.movie.MovieGenres;
import com.recody.recodybackend.movie.web.MovieHTTPAPI;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
class MovieGenreSynchronizer {
    
    public static final long WAITING_TIME_MILLIS = 5000L;
    private final CatalogGenreRepository genreRepository;
    private final CatalogGenreMapper genreMapper;
    private final WebClient webClient;
    
    public MovieGenreSynchronizer(CatalogGenreRepository genreRepository,
                                  CatalogGenreMapper genreMapper, @Qualifier( "MovieServiceWebClient" ) WebClient webClient) {
        this.genreRepository = genreRepository;
        this.genreMapper = genreMapper;
        this.webClient = webClient;
    }
    
    @Async
    @Transactional( Transactional.TxType.REQUIRES_NEW )
    public void synchronize() {
        log.debug( "영화 장르 동기화를 시도합니다.: {}", WAITING_TIME_MILLIS );
        try {
            Thread.sleep( WAITING_TIME_MILLIS );
        } catch ( InterruptedException e ) {
            throw new RuntimeException( e );
        }
        
        webClient.get()
                 .uri( builder -> builder.path( MovieHTTPAPI.getGenres ).build() )
                 .retrieve()
                 .bodyToMono( MovieGenres.class )
                 .subscribe( genres -> {
                    List<MovieGenre> values = genres.getValues();
                    for (MovieGenre genre : values) {
                        String genreId = genre.getGenreId();
                        Optional<CatalogGenreEntity> optionalGenreEntity = genreRepository.findById( genreId );
                        if ( optionalGenreEntity.isEmpty() ) {
                            CatalogGenreEntity newEntity = genreMapper.newEntity( genre );
                            CatalogGenreEntity savedEntity = genreRepository.save( newEntity );
                            log.info( "영화 장르를 저장합니다.: {}", savedEntity );
                        }
                    }
                }, cause -> {throw new RuntimeException( "장르를 가져오지 못하였습니다.", cause);} );
    }
}
