package com.recody.recodybackend.record.data.rating;

import com.recody.recodybackend.record.data.content.RecordContentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RecordRatingRepository extends JpaRepository<RecordRatingEntity, String> {
    
    Optional<RecordRatingEntity> findByUserIdAndContent(Long userId, RecordContentEntity content);
    
}
