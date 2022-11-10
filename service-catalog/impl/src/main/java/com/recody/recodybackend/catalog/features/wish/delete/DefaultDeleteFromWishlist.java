package com.recody.recodybackend.catalog.features.wish.delete;

import com.recody.recodybackend.catalog.data.wish.WishEntity;
import com.recody.recodybackend.catalog.data.wish.WishRepository;
import com.recody.recodybackend.catalog.data.category.CategoryEntity;
import com.recody.recodybackend.catalog.data.category.CategoryMapper;
import com.recody.recodybackend.catalog.data.content.CatalogContentEntity;
import com.recody.recodybackend.catalog.data.content.CatalogContentRepository;
import com.recody.recodybackend.exceptions.ContentNotFoundException;
import com.recody.recodybackend.common.contents.BasicCategory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
class DefaultDeleteFromWishlist implements DeleteFromWishlistHandler {
    
    
    private final CatalogContentRepository contentRepository;
    private final WishRepository wishRepository;
    private final CategoryMapper categoryMapper;
    
    @Override
    @Transactional
    public void handle(DeleteFromWishlist command) {
        log.debug( "handling command: {}", command );
        Long userId = command.getUserId();
        String contentId = command.getContentId().getContentId();
        BasicCategory category = command.getContentId().parseCategory();
        CategoryEntity categoryEntity = categoryMapper.map( category );
        Optional<CatalogContentEntity> optionalContent
                = contentRepository.findByContentIdAndCategory( contentId, categoryEntity );
        
        if ( optionalContent.isEmpty() ) {
            throw new ContentNotFoundException();
        }
        CatalogContentEntity catalogContent = optionalContent.get();
        Optional<WishEntity> optionalWish = wishRepository.findByCatalogContentAndUserId( catalogContent, userId );
        
        if ( optionalWish.isEmpty() ) {
            log.debug( "이미 위시리스트에 없음" );
        }
        else {
            wishRepository.delete( optionalWish.get() );
            log.debug( "{} 유저가 {} 작품에 위시를 제거함", userId, contentId );
        }
    }
}
