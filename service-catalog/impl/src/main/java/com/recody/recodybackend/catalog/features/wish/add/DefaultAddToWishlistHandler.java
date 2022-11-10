package com.recody.recodybackend.catalog.features.wish.add;

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
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
class DefaultAddToWishlistHandler implements AddToWishlistHandler {
    
    private final CatalogContentRepository contentRepository;
    private final WishRepository wishRepository;
    private final CategoryMapper categoryMapper;
    
    @Override
    @Transactional
    public UUID handle(AddToWishlist command) {
        log.debug("handling command: {}", command);
        Long userId = command.getUserId();
        String contentId = command.getContentId().getContentId();
        BasicCategory category = command.getContentId().parseCategory();
        CategoryEntity categoryEntity = categoryMapper.map(category);
        Optional<CatalogContentEntity> optionalContent
                = contentRepository.findByContentIdAndCategory(contentId, categoryEntity);
        
        if ( optionalContent.isEmpty() ) {
            log.warn("작품을 찾을 수 없습니다.");
            throw new ContentNotFoundException();
        }
        
        CatalogContentEntity catalogContent = optionalContent.get();
        WishEntity wishEntity = WishEntity.builder().userId(userId).catalogContent(catalogContent).build();
        
        Optional<WishEntity> optionalWish = wishRepository.findByCatalogContentAndUserId(catalogContent, userId);
        
        if ( optionalWish.isPresent() ) {
            UUID wishId = optionalWish.get().getId();
            log.debug("이미 위시가 존재하므로 id 를 바로 반환: {}", wishId);
            return wishId;
        }
        else {
            WishEntity savedWish = wishRepository.save(wishEntity);
            log.debug("{} 님이 {}를 위시리스트에 추가하였습니다.: {}", userId, contentId, savedWish.getId());
            return savedWish.getId();
        }
    }
}
