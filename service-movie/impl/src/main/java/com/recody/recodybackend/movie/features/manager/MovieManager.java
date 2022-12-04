package com.recody.recodybackend.movie.features.manager;

import com.recody.recodybackend.movie.Actor;
import com.recody.recodybackend.movie.Director;
import com.recody.recodybackend.movie.Movie;
import com.recody.recodybackend.movie.features.fetchmoviecredit.dto.TMDBCast;
import com.recody.recodybackend.movie.features.fetchmoviecredit.dto.TMDBCrew;
import com.recody.recodybackend.movie.MovieInfo;
import com.recody.recodybackend.movie.features.getmoviedetail.dto.TMDBMovieDetail;
import com.recody.recodybackend.movie.features.searchmovies.dto.TMDBMovieSearchNode;
import com.recody.recodybackend.movie.features.tmdb.TMDBMovieID;

import java.util.Locale;
import java.util.Optional;

/**
 * 여러가지 영화 정보를 등록하는 Registrar 를 사용하기 위한 편의 인터페이스.
 * @author motive
 */
public interface MovieManager extends MovieLoader<TMDBMovieID>, DetailedMovieRegistrar<TMDBMovieDetail> {
    
    Optional<Movie> load(TMDBMovieID sourceIdentifier, Locale locale);
    
    MovieRegistrar<TMDBMovieSearchNode> movie();
    
    MovieInfoRegistrar<MovieInfo, TMDBMovieDetail> info();
    
    MovieInfoRegistrar<Actor, TMDBCast> actor();
    
    MovieInfoRegistrar<Director, TMDBCrew> director();
    
}
