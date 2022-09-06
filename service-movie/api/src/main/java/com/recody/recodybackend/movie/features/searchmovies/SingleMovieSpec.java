package com.recody.recodybackend.movie.features.searchmovies;

import com.recody.recodybackend.movie.general.MovieGenre;
import com.recody.recodybackend.movie.general.MovieSource;
import lombok.*;

import java.util.List;

@Setter(AccessLevel.PRIVATE)
@Getter
@Builder(toBuilder = true)
public class SingleMovieSpec {
    private final Integer movieId;
    
    private MovieSource source;
    
    private String originalLanguage;
    
    private String originalTitle;
    private String title;
    
    private String overview;
    private String posterPath;
    private String releaseDate;
    
    
    /* */
    @Setter(AccessLevel.PUBLIC)
    private List<MovieGenre> genres;
    @Setter(AccessLevel.PUBLIC)
    private Integer rootId;
}
