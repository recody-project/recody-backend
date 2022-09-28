package com.recody.recodybackend.movie.data.movie;

import com.recody.recodybackend.movie.Movie;
import com.recody.recodybackend.movie.data.genre.MovieGenreMapper;
import com.recody.recodybackend.movie.data.productioncountry.ProductionCountryMapper;
import com.recody.recodybackend.movie.data.spokenlanguage.SpokenLanguageMapper;
import com.recody.recodybackend.movie.features.getmoviedetail.TMDBMovieDetail;
import com.recody.recodybackend.movie.features.getmoviedetail.TMDBMovieGenre;
import com.recody.recodybackend.movie.general.MovieGenre;
import com.recody.recodybackend.movie.general.MovieSource;
import org.mapstruct.*;

@Mapper(componentModel = "spring",
        imports = {MovieSource.class},
        uses = {MovieGenreMapper.class,
                ProductionCountryMapper.class,
                SpokenLanguageMapper.class})
public interface MovieEntityMapper {
    
    @Mapping(target = "rootId", ignore = true)
    @Mapping(target = "source", expression = "java(MovieSource.TMDB)")
    @Mapping(target = "tmdbId", source = "tmdbMovieDetail.id")
    Movie map(TMDBMovieDetail tmdbMovieDetail);
    
    
    @Mapping(target = "tmdbId", source = "id", conditionExpression = "java(movie.getSource() == MovieSource.TMDB)")
    MovieEntity toEntity(Movie movie);
    
    
    @Mapping(target = "source", expression = "java((s.equals(MovieSource.TMDB)) ? MovieSource.TMDB : null)")
    @Mapping(target = "rootId", ignore = true)
    @Mapping(target = "id", source = "movie.tmdbId", conditionExpression = "java(MovieSource.TMDB.equals(s))")
    Movie map(MovieEntity movie, MovieSource s);
    
    
    default MovieGenre toGenre(TMDBMovieGenre tmdbMovieGenre){
        MovieGenre movieGenre = new MovieGenre();
        movieGenre.setGenreId(tmdbMovieGenre.getId());
        movieGenre.setGenreName(tmdbMovieGenre.getName());
        movieGenre.setSource(MovieSource.TMDB);
        return movieGenre;
    }
}
