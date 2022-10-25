package com.recody.recodybackend.movie.data.movie;

import com.recody.recodybackend.common.contents.BasicCategory;
import com.recody.recodybackend.common.contents.movie.Movie;
import com.recody.recodybackend.common.contents.movie.MovieSource;
import com.recody.recodybackend.movie.data.genre.MovieGenreMapper;
import com.recody.recodybackend.movie.data.title.MovieTitleMapper;
import com.recody.recodybackend.movie.features.getmoviedetail.MovieInfo;
import com.recody.recodybackend.movie.features.searchmovies.dto.TMDBMovieSearchNode;
import com.recody.recodybackend.movie.features.tmdb.TMDB;
import com.recody.recodybackend.movie.web.TMDBSearchedMovie;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.*;

import java.util.List;
import java.util.Locale;

@Mapper(componentModel = "spring",
        builder = @Builder(disableBuilder = true),
        imports = {TMDB.class, MovieSource.class, BasicCategory.class},
        uses = {MovieGenreMapper.class, MovieTitleMapper.class})
@Slf4j
public abstract class MovieMapper {
    
    @Named("posterPathMapper")
    public String map(String posterPath) {
        return TMDB.fullPosterPath(posterPath);
    }
    
    
    // toMovieInfo() 를 사용하지 않게 만들기 위해 설정
    @IterableMapping(qualifiedByName = "ToMovieMapper")
    public abstract List<Movie> toMovie(List<MovieEntity> movieEntities, @Context Locale locale);
    
    public abstract TMDBSearchedMovie toTMDBMovie(MovieEntity entity, @Context Locale locale);
    
    public abstract List<TMDBSearchedMovie> toTMDBMovie(List<MovieEntity> entity, @Context Locale locale);
    
    public abstract List<TMDBSearchedMovie> toTMDBMovie(List<TMDBMovieSearchNode> nodes);
    
    /**
     * 아직 저장되지 않은 영화에 대해 매핑한다.
     */
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "runtime", ignore = true)
    @Mapping(target = "revenue", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "spokenLanguages", ignore = true)
    @Mapping(target = "productionCountries", ignore = true)
    @Mapping(target = "directors", ignore = true)
    @Mapping(target = "actors", ignore = true)
    @Mapping(target = "genres", ignore = true)
    @Mapping(target = "title", source = "tmdbMovie.title")
    @Mapping(target = "tmdbId", source = "tmdbMovie.id")
    @Mapping(target = "posterPath", expression = "java(TMDB.fullPosterPath(tmdbMovie.getPosterPath()))")
    public abstract MovieEntity newEntity(TMDBMovieSearchNode tmdbMovie, @Context Locale locale);
    
    @Named("ToMovieMapper")
    @Mapping(target = "category", expression = "java(com.recody.recodybackend.common.contents.BasicCategory.Movie)")
    @Mapping(target = "contentId", source = "movie.id")
    @Mapping(target = "title", source = "movie.title")
    public abstract Movie toMovie(MovieEntity movie, @Context Locale locale);
    
    /**
     * api 에서 받아온 결과를 그대로 넘겨줄때 사용한다.
     */
    @Mapping(target = "tmdbId", source = "node.id")
    @Mapping(target = "posterPath", source = "node.posterPath", qualifiedByName = "posterPathMapper")
    public abstract TMDBSearchedMovie toTMDBMovie(TMDBMovieSearchNode node);
    
    
    @Mapping(target = "category", expression = "java(BasicCategory.Movie)")
    @Mapping(target = "source", expression = "java(MovieSource.TMDB)")
    @Mapping(target = "title", source = "entity.title")
    @Mapping(target = "contentId", source = "entity.id")
    @Mapping(target = "genres", source = "entity.genres")
    public abstract MovieInfo toMovieInfo(MovieEntity entity, @Context Locale locale);
    
}
