package com.recody.recodybackend.drama.data.genre;

import com.recody.recodybackend.common.Recody;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.scheduling.annotation.Async;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public interface DramaGenreCodeRepository extends JpaRepository<DramaGenreCodeEntity, String> {
    
    Optional<DramaGenreCodeEntity> findByTmdbGenreId(Integer tmdbGenreId);
    
    /**
     * tmdb id 들을 받아서 장르 코드 엔티티들을 반환하는 비동기 메서드.
     */
    @Async(value = Recody.DRAMA_TASK_EXECUTOR )
    CompletableFuture<List<DramaGenreCodeEntity>> findByTmdbGenreIdIn(List<Integer> tmdbGenreId);

}
