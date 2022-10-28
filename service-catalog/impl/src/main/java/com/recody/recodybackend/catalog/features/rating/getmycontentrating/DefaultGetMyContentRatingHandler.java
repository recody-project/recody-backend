package com.recody.recodybackend.catalog.features.rating.getmycontentrating;

import com.recody.recodybackend.catalog.data.content.CatalogContentEntity;
import com.recody.recodybackend.catalog.data.rating.RatingEntity;
import com.recody.recodybackend.catalog.data.rating.RatingMapper;
import com.recody.recodybackend.catalog.data.rating.RatingRepository;
import com.recody.recodybackend.catalog.features.Rating;
import com.recody.recodybackend.catalog.features.RatingScore;
import com.recody.recodybackend.catalog.features.UnRated;
import com.recody.recodybackend.catalog.features.manager.ContentLoader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
class DefaultGetMyContentRatingHandler implements GetMyContentRatingHandler{
    
    private final RatingRepository ratingRepository;
    private final ContentLoader<CatalogContentEntity> contentLoader;
    private final RatingMapper ratingMapper;
    
    @Override
    @Transactional
    public Rating handle(GetMyContentRating command) {
        Long userId = command.getUserId();
        CatalogContentEntity contentEntity = contentLoader.loadEntity( command.getContentId() );
        Optional<RatingEntity> optionalRating = ratingRepository.findByUserIdAndContent( userId, contentEntity );
        
        if ( optionalRating.isEmpty() ) {
            return new UnRated();
        }
        
        RatingScore ratingScore = ratingMapper.map( optionalRating.get() );
        log.info( "유저가 작품에 남긴 별점을 반환합니다.");
        return ratingScore;
    }
}
