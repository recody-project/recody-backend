package com.recody.recodybackend.movie.features.searchmovies;

import com.recody.recodybackend.common.contents.BasicCategory;
import com.recody.recodybackend.common.exceptions.ApplicationException;
import com.recody.recodybackend.movie.Movie;
import com.recody.recodybackend.movie.data.movie.MovieEntity;
import com.recody.recodybackend.movie.data.title.MovieTitleEntity;
import com.recody.recodybackend.movie.features.resolvegenres.fromapi.GetMovieGenreFromTMDBApiHandler;
import com.recody.recodybackend.movie.features.tmdb.TMDB;
import com.recody.recodybackend.movie.features.searchmovies.dto.TMDBMovieSearchNode;
import com.recody.recodybackend.movie.general.MovieGenre;
import com.recody.recodybackend.movie.general.MovieSource;
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
public abstract class TMDBMovieSearchMapper {
    
    
    @Autowired
    private GetMovieGenreFromTMDBApiHandler resolver;
    
    @Named("genreMapper")
    public List<MovieGenre> map(List<Integer> genreIds) {
        ArrayList<MovieGenre> movieGenres = new ArrayList<>();
        for (Integer genreId : genreIds) {
            movieGenres.add(resolver.getMovieGenre(genreId));
        }
        return movieGenres;
    }
    
    @Named("posterPathMapper")
    public String map(String posterPath) {
        return TMDB.fullPosterPath(posterPath);
    }
    
    public List<Movie> mapList(List<TMDBMovieSearchNode> tmdbMovies) {
        ArrayList<Movie> movieDetails = new ArrayList<>();
        for (TMDBMovieSearchNode tmdbMovie : tmdbMovies) {
            movieDetails.add(this.map(tmdbMovie));
        }
        return movieDetails;
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
    
    @Mapping(target = "contentId", ignore = true)
    @Mapping(target = "category", expression = "java(BasicCategory.Movie)")
    @Mapping(target = "posterPath", source = "tmdbMovie.posterPath", qualifiedByName = "posterPathMapper")
    public abstract Movie map(TMDBMovieSearchNode tmdbMovie);
    
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
    public abstract MovieEntity mapEntity(TMDBMovieSearchNode tmdbMovie, Locale locale);
    
    public List<MovieEntity> mapEntityList(List<TMDBMovieSearchNode> tmdbMovies, Locale locale){
        ArrayList<MovieEntity> movieEntities = new ArrayList<>();
        for (TMDBMovieSearchNode tmdbMovie : tmdbMovies) {
            MovieEntity movieEntity = this.mapEntity(tmdbMovie, locale);
            movieEntities.add(movieEntity);
        }
        return movieEntities;
    }
    
}
