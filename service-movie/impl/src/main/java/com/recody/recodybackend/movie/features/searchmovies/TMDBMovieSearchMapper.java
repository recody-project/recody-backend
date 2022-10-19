package com.recody.recodybackend.movie.features.searchmovies;

import com.recody.recodybackend.movie.MovieDetail;
import com.recody.recodybackend.movie.features.resolvegenres.fromapi.GetMovieGenreFromTMDBApiHandler;
import com.recody.recodybackend.movie.features.tmdb.TMDB;
import com.recody.recodybackend.movie.features.searchmovies.dto.TMDBMovieSearchNode;
import com.recody.recodybackend.movie.general.MovieGenre;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public abstract class TMDBMovieSearchMapper {
    
    @Mapping(target = "genres", source = "tmdbMovie.genreIds", qualifiedByName = "genreMapper")
    @Mapping(target = "tmdbId", source = "tmdbMovie.id")
    @Mapping(target = "posterPath", source = "tmdbMovie.posterPath", qualifiedByName = "posterPathMapper")
    public abstract MovieDetail map(TMDBMovieSearchNode tmdbMovie);
    
    public List<MovieDetail> mapList(List<TMDBMovieSearchNode> tmdbMovies){
        ArrayList<MovieDetail> movieDetails = new ArrayList<>();
        for (TMDBMovieSearchNode tmdbMovie : tmdbMovies) {
            movieDetails.add(this.map(tmdbMovie));
        }
        return movieDetails;
    }
    
    
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
    
}
