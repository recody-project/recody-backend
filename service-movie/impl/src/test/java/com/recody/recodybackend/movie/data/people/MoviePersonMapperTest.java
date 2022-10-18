package com.recody.recodybackend.movie.data.people;

import com.recody.recodybackend.movie.RecodyMovieApplication;
import com.recody.recodybackend.movie.data.movie.MovieEntity;
import com.recody.recodybackend.movie.features.getmoviecredit.Actor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@ContextConfiguration(classes = RecodyMovieApplication.class)
@ExtendWith(MockitoExtension.class)
class MoviePersonMapperTest {
    
    public static final String ENGLISH_NAME = "emem";
    @Autowired
    MoviePersonMapper realPersonMapper;
    
    @Autowired
    MoviePersonRepository realPersonRepository;
    
    @Test
    @DisplayName("PersonEntity 에 NameEntity 도 세팅되어 있다.")
    void mapToPersonEntity() {
        // given
        Actor actor = Actor.builder().name(ENGLISH_NAME).build();
        // when
        MoviePersonEntity moviePersonEntity = realPersonMapper.newPersonEntity(actor);
        // then
        MoviePersonNameEntity name = moviePersonEntity.getName();
        assertThat(name.getEnglishName()).isEqualTo(ENGLISH_NAME);
    }
    
    @Test
    @DisplayName("Actor 를 MovieEntity 와 함께 ActorEntity 로 매핑할 수 있다. ")
    void mapActor() {
        // given
        Actor actor = Actor.builder().name(ENGLISH_NAME).build();
        MovieEntity movieEntity = MovieEntity.builder().build();
        // when
        MovieActorEntity actorEntity = realPersonMapper.newActorEntity(actor, movieEntity);
    
        // then
        assertThat(actorEntity.getMovie()).isEqualTo(movieEntity);
        assertThat(actorEntity.getPerson().getName().getEnglishName()).isEqualTo(ENGLISH_NAME);
    }
}