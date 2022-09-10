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
    
    
    /* 장르와 root Id 는 외부에서 resolve 한 뒤에 세팅된다. */
    @Setter(AccessLevel.PUBLIC)
    private List<MovieGenre> genres;
    @Setter(AccessLevel.PUBLIC)
    private Integer rootId;
}
