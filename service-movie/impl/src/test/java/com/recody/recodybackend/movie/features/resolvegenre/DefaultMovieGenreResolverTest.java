package com.recody.recodybackend.movie.features.resolvegenre;

import com.recody.recodybackend.common.exceptions.ApplicationException;
import com.recody.recodybackend.movie.RecodyMovieApplication;
import com.recody.recodybackend.movie.data.genre.MovieGenreCodeEntity;
import com.recody.recodybackend.movie.data.genre.MovieGenreCodeRepository;
import com.recody.recodybackend.movie.data.genre.MovieGenreMapper;
import com.recody.recodybackend.movie.features.getmoviedetail.dto.TMDBMovieGenre;
import com.recody.recodybackend.movie.data.genre.MovieGenreCodeManager;
import com.recody.recodybackend.movie.features.resolvegenres.MovieGenreResolver;
import com.recody.recodybackend.movie.features.resolvegenre.fromapi.GetTMDBMovieGenresHandler;
import com.recody.recodybackend.movie.general.MovieGenre;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
@ContextConfiguration(classes = RecodyMovieApplication.class)
@ExtendWith(MockitoExtension.class)
class DefaultMovieGenreResolverTest {
    
    private static final String TMDB_GENRE_NAME = "action";
    public static final String GENRE_ID = "mg-1";
    public static final int TMDB_GENRE_ID = 18;
    private List<TMDBMovieGenre> tmdbMovieGenres;
    private List<MovieGenreCodeEntity> entities;
    
    MovieGenreResolver movieGenreResolver;
    
    @Mock
    MovieGenreCodeManager manager;
    
    @Mock
    GetTMDBMovieGenresHandler getTMDBMovieGenresHandler;
    
    @Autowired
    MovieGenreCodeRepository movieGenreCodeRepository;
    
    @Autowired
    MovieGenreMapper genreMapper;
    
    
    @BeforeEach
    void before() {
    
        TMDBMovieGenre tmdbMovieGenre = new TMDBMovieGenre(TMDB_GENRE_ID, TMDB_GENRE_NAME);
        tmdbMovieGenres = List.of(tmdbMovieGenre);
        
        
        
        MovieGenreCodeEntity entity = MovieGenreCodeEntity.builder()
                                                         .tmdbGenreId(TMDB_GENRE_ID)
                                                         .tmdbGenreName(TMDB_GENRE_NAME)
                                                         .build();
    
        entities = List.of(entity);
    
        when(getTMDBMovieGenresHandler.getTMDBMovieGenres())
                .thenReturn(tmdbMovieGenres);
        when(manager.register(tmdbMovieGenres))
                .thenReturn(entities);
        
        movieGenreResolver = new DefaultMovieGenreResolver(manager, getTMDBMovieGenresHandler, movieGenreCodeRepository, genreMapper);
        movieGenreResolver.initTMDBGenres();
    
    }
    
    @Test
    void resolveTMDBGenre() {
        
        TMDBMovieGenre tmdbMovieGenre = movieGenreResolver.toTMDBGenre(TMDB_GENRE_ID);
        assertThat(tmdbMovieGenre).isNotNull();
        System.out.println("tmdbMovieGenre = " + tmdbMovieGenre);
        
    }
    
    @Test
    void resolveMovieGenre() {
        MovieGenre movieGenre = movieGenreResolver.toMovieGenre(GENRE_ID);
        assertThat(movieGenre).isNotNull();
        System.out.println("movieGenre = " + movieGenre);
    
    }
    
    @Test
    @DisplayName("저장하지 않은 id 를 주면 예외를 일으킨다.")
    void resolveMovieGenre2() {
        Exception exception = catchException(() -> movieGenreResolver.toMovieGenre(123));
        assertThat(exception).isInstanceOf(ApplicationException.class);
    
    }
    
    @Test
    void testResolveMovieGenre() {
    
        MovieGenre movieGenre = movieGenreResolver.toMovieGenre(TMDB_GENRE_ID);
        assertThat(movieGenre).isNotNull();
        System.out.println("movieGenre = " + movieGenre);
    }
}