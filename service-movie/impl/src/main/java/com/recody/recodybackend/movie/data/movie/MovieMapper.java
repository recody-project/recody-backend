package com.recody.recodybackend.movie.data.movie;

import com.recody.recodybackend.common.contents.BasicCategory;
import com.recody.recodybackend.common.exceptions.ApplicationException;
import com.recody.recodybackend.movie.Movie;
import com.recody.recodybackend.movie.data.title.MovieTitleEntity;
import com.recody.recodybackend.movie.features.resolvegenres.fromapi.GetMovieGenreFromTMDBApiHandler;
import com.recody.recodybackend.movie.features.searchmovies.dto.TMDBMovieSearchNode;
import com.recody.recodybackend.movie.features.tmdb.TMDB;
import com.recody.recodybackend.movie.general.MovieGenre;
import com.recody.recodybackend.movie.general.MovieSource;
import com.recody.recodybackend.movie.web.TMDBSearchedMovie;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Mapper(componentModel = "spring", imports = {MovieSource.class, BasicCategory.class})
@Slf4j
public abstract class MovieMapper {
    
    
    @Autowired
    private GetMovieGenreFromTMDBApiHandler resolver;
    
    @Named("posterPathMapper")
    public String map(String posterPath) {
        return TMDB.fullPosterPath(posterPath);
    }
    
    public List<MovieEntity> mapEntityList(List<TMDBMovieSearchNode> tmdbMovies, Locale locale) {
        ArrayList<MovieEntity> movieEntities = new ArrayList<>();
        for (TMDBMovieSearchNode tmdbMovie : tmdbMovies) {
            MovieEntity movieEntity = this.toEntity(tmdbMovie, locale);
            movieEntities.add(movieEntity);
        }
        return movieEntities;
    }
    
    @Named("genreMapper")
    public List<MovieGenre> mapGenres(List<Integer> genreIds) {
        ArrayList<MovieGenre> movieGenres = new ArrayList<>();
        for (Integer genreId : genreIds) {
            movieGenres.add(resolver.getMovieGenre(genreId));
        }
        return movieGenres;
    }
    
    @Named("MovieTitleMapper")
    public MovieTitleEntity mapTitle(String title, Locale locale) {
        log.debug("mapping title: {}", title);
        MovieTitleEntity movieTitleEntity;
        if (locale.equals(Locale.ENGLISH)) {
            movieTitleEntity = MovieTitleEntity.builder().englishTitle(title).build();
        } else if (locale.equals(Locale.KOREAN)) {
            movieTitleEntity = MovieTitleEntity.builder().koreanTitle(title).build();
        } else throw new ApplicationException(HttpStatus.INTERNAL_SERVER_ERROR, "영화 제목을 저장하지 못했습니다.");
        log.debug("mapped title: {}", movieTitleEntity);
        return movieTitleEntity;
    }
    
    public String mapTitle(MovieEntity entity, Locale locale) {
        if (locale.equals(Locale.KOREAN)) {
            String koreanTitle = entity.getTitle().getKoreanTitle();
            if (koreanTitle == null) {
                return entity.getOriginalTitle();
            }
            return koreanTitle;
        }
        if (locale.equals(Locale.ENGLISH)) {
            String englishTitle = entity.getTitle().getEnglishTitle();
            if (englishTitle == null) {
                return entity.getOriginalTitle();
            }
            return englishTitle;
        }
        return entity.getOriginalTitle();
    }
    
    public List<Movie> toMovieList(List<TMDBMovieSearchNode> tmdbMovies) {
        ArrayList<Movie> movieDetails = new ArrayList<>();
        for (TMDBMovieSearchNode tmdbMovie : tmdbMovies) {
            movieDetails.add(this.toMovie(tmdbMovie));
        }
        return movieDetails;
    }
    
    public List<Movie> toMovieList(List<MovieEntity> movieEntities, Locale locale) {
        ArrayList<Movie> movies = new ArrayList<>();
        for (MovieEntity movieEntity : movieEntities) {
            Movie movie = this.toMovie(movieEntity, locale);
            movies.add(movie);
        }
        return movies;
    }
    
    public List<TMDBSearchedMovie> toTMDBMovieList(List<TMDBMovieSearchNode> nodes) {
        ArrayList<TMDBSearchedMovie> tmdbSearchedMovies = new ArrayList<>();
        for (TMDBMovieSearchNode node : nodes) {
            TMDBSearchedMovie tmdbSearchedMovie = this.toTMDBMovie(node);
            tmdbSearchedMovies.add(tmdbSearchedMovie);
        }
        return tmdbSearchedMovies;
    }
    
    @Mapping(target = "contentId", expression = "java(String.valueOf(tmdbMovie.getId()))")
    // tmdb id 는 content id 와 다르다.
    @Mapping(target = "category", expression = "java(BasicCategory.Movie)")
    @Mapping(target = "posterPath", source = "tmdbMovie.posterPath", qualifiedByName = "posterPathMapper")
    public abstract Movie toMovie(TMDBMovieSearchNode tmdbMovie);
    
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "runtime", ignore = true)
    @Mapping(target = "revenue", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "spokenLanguages", ignore = true)
    @Mapping(target = "productionCountries", ignore = true)
    @Mapping(target = "directors", ignore = true)
    @Mapping(target = "actors", ignore = true)
    @Mapping(target = "genres", ignore = true)
    @Mapping(target = "title", expression = "java(this.mapTitle(tmdbMovie.getTitle(), locale))")
    @Mapping(target = "tmdbId", source = "tmdbMovie.id")
    @Mapping(target = "posterPath", source = "tmdbMovie.posterPath", qualifiedByName = "posterPathMapper")
    public abstract MovieEntity toEntity(TMDBMovieSearchNode tmdbMovie, Locale locale);
    
    @Mapping(target = "category", expression = "java(com.recody.recodybackend.common.contents.BasicCategory.Movie)")
    @Mapping(target = "contentId", source = "movie.id")
    @Mapping(target = "title", expression = "java(this.mapTitle(movie, locale))")
    public abstract Movie toMovie(MovieEntity movie, Locale locale);
    
    @Mapping(target = "tmdbId", source = "node.id")
    @Mapping(target = "posterPath", source = "node.posterPath", qualifiedByName = "posterPathMapper")
    public abstract TMDBSearchedMovie toTMDBMovie(TMDBMovieSearchNode node);
}
