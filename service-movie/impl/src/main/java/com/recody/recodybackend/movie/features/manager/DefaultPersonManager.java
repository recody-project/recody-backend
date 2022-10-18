package com.recody.recodybackend.movie.features.manager;

import com.recody.recodybackend.movie.data.people.MoviePersonEntity;
import com.recody.recodybackend.movie.data.people.MoviePersonMapper;
import com.recody.recodybackend.movie.data.people.MoviePersonRepository;
import com.recody.recodybackend.movie.features.getmoviecredit.Actor;
import com.recody.recodybackend.movie.features.getmoviecredit.Director;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.concurrent.CompletableFuture;

@Component
@RequiredArgsConstructor
@Slf4j
class DefaultPersonManager implements PersonManager{
    
    private final MoviePersonMapper moviePersonMapper;
    private final MoviePersonRepository personRepository;
    
    @Override
    @Transactional
    public MoviePersonEntity register(Actor actor) {
        log.debug("registering actor as person: {}", actor);
        MoviePersonEntity personEntity = moviePersonMapper.newPersonEntity(actor);
        MoviePersonEntity savedPersonEntity = personRepository.save(personEntity);
        log.info("영화 서비스에서 인물을 등록하였습니다.: {}", savedPersonEntity);
        return savedPersonEntity;
    }
    
    @Override
    @Async
    public CompletableFuture<MoviePersonEntity> registerAsync(Actor actor) {
        return CompletableFuture.completedFuture(this.register(actor));
    }
    
    @Override
    @Transactional
    public MoviePersonEntity register(Director director) {
        log.debug("registering director as person: {}", director);
        MoviePersonEntity personEntity = moviePersonMapper.newPersonEntity(director);
        MoviePersonEntity savedPersonEntity = personRepository.save(personEntity);
        log.info("영화 서비스에서 인물을 등록하였습니다.: {}", savedPersonEntity);
    
        return savedPersonEntity;
    }
    
    @Override
    @Async
    public CompletableFuture<MoviePersonEntity> registerAsync(Director director) {
        return CompletableFuture.completedFuture(this.register(director));
    }
}
