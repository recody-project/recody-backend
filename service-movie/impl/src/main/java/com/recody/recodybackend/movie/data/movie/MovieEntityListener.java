package com.recody.recodybackend.movie.data.movie;

import com.recody.recodybackend.movie.events.MovieCreated;
import com.recody.recodybackend.movie.features.projection.MovieEventPublisher;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.PostPersist;

@Component
@Slf4j
@NoArgsConstructor
public class MovieEntityListener {
    
    private MovieEventPublisher movieEventPublisher;
    
    @Autowired
    public MovieEntityListener(MovieEventPublisher movieEventPublisher) {
        this.movieEventPublisher = movieEventPublisher;
    }
    
    @PostPersist
    void postPersist(MovieEntity movieEntity){
        String posterPath = movieEntity.getPosterPath();
        String id = movieEntity.getId();
        MovieCreated event = MovieCreated.builder()
                                         .contentId( id )
                                         .posterUrl( posterPath )
                                         .build();
        movieEventPublisher.publish( event );
    }
}
