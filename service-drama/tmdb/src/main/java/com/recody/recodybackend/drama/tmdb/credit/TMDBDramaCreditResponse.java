package com.recody.recodybackend.drama.tmdb.credit;

import com.recody.recodybackend.drama.tmdb.TMDB;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor( access = AccessLevel.PROTECTED )
public class TMDBDramaCreditResponse {
    
    private List<TMDBDramaCast> cast;
    private List<TMDBDramaCrew> crew;
    
    
    /**
     * @return 부서가 ACTING 이고 최대 5 개까지만 반환.
     */
    public List<TMDBDramaCast> getCast() {
        return cast.stream()
                   .filter( it -> it.getKnownForDepartment().equals( TMDB.ACTING ) )
                   .limit( TMDB.ACTOR_MAX_SIZE ).collect( Collectors.toList() );
    }
    
    /**
     * @return 부서가 DIRECTING 이고 최대 5개까지만 반환.
     */
    public List<TMDBDramaCrew> getCrew() {
        return crew.stream()
                       .filter( it -> it.getKnownForDepartment().equals( TMDB.DIRECTING ) )
                       .limit( TMDB.DIRECTOR_MAX_SIZE )
                       .collect( Collectors.toList() );
    }
    
    @Override
    public String toString() {
        return "{\"TMDBDramaCreditResponse\":{"
               + "\"cast\":" + cast
               + ", \"crew\":" + crew
               + "}}";
    }
}
