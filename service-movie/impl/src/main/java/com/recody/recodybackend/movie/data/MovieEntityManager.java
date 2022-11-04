package com.recody.recodybackend.movie.data;

import com.recody.recodybackend.movie.data.genre.MovieGenreCodeEntity;
import com.recody.recodybackend.movie.data.genre.MovieGenreEntity;
import com.recody.recodybackend.movie.data.movie.MovieEntity;
import com.recody.recodybackend.movie.data.people.MovieActorEntity;
import com.recody.recodybackend.movie.data.people.MovieDirectorEntity;
import com.recody.recodybackend.movie.data.people.MoviePersonEntity;
import com.recody.recodybackend.movie.features.getmoviecredit.dto.TMDBCast;
import org.springframework.scheduling.annotation.Async;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Transactional
public interface MovieEntityManager {
    
    @Async
    @Transactional
    default CompletableFuture<List<MovieGenreEntity>> saveAsync(MovieEntity movieEntity, List<MovieGenreCodeEntity> genreCodeEntities){
        return CompletableFuture.completedFuture(this.saveMovieGenre(movieEntity, genreCodeEntities));
    }
    

    default List<MovieGenreEntity> saveMovieGenre(MovieEntity movieEntity, List<MovieGenreCodeEntity> genreCodes) {
        return genreCodes.stream()
                         .map(genreCodeEntity -> this.saveMovieGenre(movieEntity, genreCodeEntity))
                         .collect(Collectors.toList());
    }
    
    
    
    
    MovieEntity upsertTitleByLocale(MovieEntity movieEntity, String title, Locale locale);
    MovieGenreEntity saveMovieGenre(MovieEntity movieEntity, MovieGenreCodeEntity genreCode);
    MovieActorEntity saveActor(MovieEntity movieEntity, MoviePersonEntity personEntity, TMDBCast cast);
    MovieDirectorEntity saveDirector(MovieEntity movieEntity, MoviePersonEntity personEntity);
    List<MovieDirectorEntity> saveDirector(MovieEntity movieEntity,List<MoviePersonEntity> personEntities);
    
}
