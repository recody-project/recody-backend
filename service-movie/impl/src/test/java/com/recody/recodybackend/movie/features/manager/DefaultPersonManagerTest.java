package com.recody.recodybackend.movie.features.manager;

import com.recody.recodybackend.movie.RecodyMovieApplication;
import com.recody.recodybackend.movie.data.people.MoviePersonEntity;
import com.recody.recodybackend.movie.data.people.MoviePersonMapper;
import com.recody.recodybackend.movie.data.people.MoviePersonRepository;
import com.recody.recodybackend.movie.features.getmoviecredit.Actor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
@ContextConfiguration(classes = RecodyMovieApplication.class)
@ExtendWith(MockitoExtension.class)
class DefaultPersonManagerTest {
    
    public static final long ID = 123L;
    
    DefaultPersonManager defaultPersonManager;
    @Mock
    MoviePersonMapper mockPersonMapper;
    
    @Mock
    MoviePersonRepository mockPersonRepository;
    
    MoviePersonEntity targetEntity;
    
    @BeforeEach
    void before() {
        targetEntity = MoviePersonEntity.builder().id(ID).build();
        
        when(mockPersonMapper.newPersonEntity(any(Actor.class))).thenReturn(targetEntity);
        when(mockPersonRepository.save( any(MoviePersonEntity.class)) ).thenReturn(targetEntity);
        
        defaultPersonManager = new DefaultPersonManager(mockPersonMapper, mockPersonRepository);
    }
    
    @Test
    @DisplayName("register 하면 id가 있는 entity 를 반환한다.")
    void register() {
        Actor actor = Actor.builder().build();
        MoviePersonEntity registeredEntity = defaultPersonManager.register(actor);
        assertThat(registeredEntity.getId()).isEqualTo(ID);
    }
}