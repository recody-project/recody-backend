package com.recody.recodybackend.movie.data;

import com.recody.recodybackend.movie.data.genre.MovieGenreCodeEntity;
import com.recody.recodybackend.movie.data.genre.MovieGenreEntity;
import com.recody.recodybackend.movie.data.genre.MovieGenreRepository;
import com.recody.recodybackend.movie.data.movie.MovieEntity;
import com.recody.recodybackend.movie.data.people.*;
import com.recody.recodybackend.movie.data.title.MovieTitleEntity;
import com.recody.recodybackend.movie.data.title.MovieTitleRepository;
import com.recody.recodybackend.movie.data.people.ActorMapper;
import com.recody.recodybackend.movie.data.people.DirectorMapper;
import com.recody.recodybackend.movie.features.getmoviecredit.dto.TMDBCast;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Slf4j
@Transactional
class DefaultMovieEntityManager implements MovieEntityManager {
    
    private final MovieTitleRepository movieTitleRepository;
    private final MovieGenreRepository movieGenreRepository;
    private final MovieActorRepository actorRepository;
    private final MovieDirectorRepository directorRepository;
    
    private final ActorMapper actorMapper;
    
    private final DirectorMapper directorMapper;
    
    @Override
    @Transactional
    public MovieEntity upsertTitleByLocale(MovieEntity movieEntity, String title, Locale locale) {
        // 타이틀이 없으면 새로 만들어 저장하고
        // 타이틀이 있으면 업데이트한다.
        Optional<MovieTitleEntity> optionalTitle = movieTitleRepository.findByMovie(movieEntity);
        
        if (optionalTitle.isEmpty()) {
            MovieTitleEntity movieTitleEntity = new MovieTitleEntity();
            movieTitleEntity.setMovie(movieEntity);
            updateTitleByLocale(movieTitleEntity, title, locale);
            MovieTitleEntity savedTitle = movieTitleRepository.save(movieTitleEntity);
            movieEntity.setTitle(savedTitle);
            log.debug("영화에 타이틀을 저장");
            return movieEntity;
        }
        
        MovieTitleEntity movieTitleEntity = optionalTitle.get();
        updateTitleByLocale(movieTitleEntity, title, locale);
        log.debug("영화에 타이틀을 업데이트");
        return movieEntity;
    }
    
    
    @Override
    @Transactional
    public MovieGenreEntity saveMovieGenre(MovieEntity movieEntity, MovieGenreCodeEntity genreCode) {
        log.debug("saving movie genre");
        MovieGenreEntity movieGenreEntity = MovieGenreEntity.builder().genre(genreCode).movie(movieEntity).build();
        return movieGenreRepository.save(movieGenreEntity);
    }

    
    @Override
    @Transactional
    public MovieActorEntity saveActor(MovieEntity movieEntity, MoviePersonEntity personEntity, TMDBCast cast) {
        log.debug("saving Actor Entity");
        MovieActorEntity newActorEntity = MovieActorEntity.builder()
                                                       .character(cast.getCharacter())
                                                       .movie(movieEntity)
                                                       .person(personEntity).build();
        
        Optional<MovieActorEntity> optionalActorEntity =
                actorRepository.findDistinctByMovieAndPerson(movieEntity, personEntity);
        
        if (optionalActorEntity.isPresent()){
            MovieActorEntity currentActorEntity = optionalActorEntity.get();
            MovieActorEntity updatedActorEntity = actorMapper.update(currentActorEntity, newActorEntity);
            log.debug("영화 배우 정보를 업데이트하고 반환합니다.");
            return updatedActorEntity;
        }
        
        return actorRepository.save(newActorEntity);
    }
    
    @Override
    @Transactional
    public MovieDirectorEntity saveDirector(MovieEntity movieEntity, MoviePersonEntity personEntity) {
        log.debug("saving Actor Entity");
        MovieDirectorEntity newDirectorEntity = MovieDirectorEntity.builder().movie(movieEntity).person(personEntity).build();
        Optional<MovieDirectorEntity> optionalDirectorEntity =
                directorRepository.findDistinctByMovieAndPerson(movieEntity, personEntity);
        if (optionalDirectorEntity.isPresent()) {
            MovieDirectorEntity currentDirectorEntity = optionalDirectorEntity.get();
            MovieDirectorEntity updatedEntity = directorMapper.update(currentDirectorEntity, newDirectorEntity);
            log.debug("영화의 감독 정보를 업데이트하고 반환합니다.");
            return updatedEntity;
        }
        return directorRepository.save(newDirectorEntity);
    }
    
    @Override
    public List<MovieDirectorEntity> saveDirector(MovieEntity movieEntity, List<MoviePersonEntity> personEntities) {
        // findAll
        // 없으면 newEntity 로 모두 저장
        // 있고 개수가 같으면 모두 업데이트
        // 있고 개수가 다르면 같은 것만 업데이트
        return null;
    }
    
    
    private void updateTitleByLocale(MovieTitleEntity movieTitleEntity, String title, Locale locale) {
        if (locale.equals(Locale.KOREAN)){
            movieTitleEntity.setKoreanTitle(title);
        }
        if (locale.equals(Locale.ENGLISH)){
            movieTitleEntity.setEnglishTitle(title);
        }
    }
}
