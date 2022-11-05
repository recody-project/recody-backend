package com.recody.recodybackend.catalog.features.wish.get;

import com.recody.recodybackend.catalog.data.content.CatalogContentMapper;
import com.recody.recodybackend.catalog.data.wish.WishEntity;
import com.recody.recodybackend.catalog.data.wish.WishRepository;
import com.recody.recodybackend.catalog.CatalogMovie;
import com.recody.recodybackend.common.contents.Content;
import com.recody.recodybackend.common.exceptions.InternalServerError;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
class DefaultGetMyWishlistHandler implements GetMyWishlistHandler {
    
    private final WishRepository wishRepository;
    private final CatalogContentMapper contentMapper;
    
    @Override
    @Transactional
    public List<Content<?>> handle(GetMyWishlist command) {
        Long userId = command.getUserId();
        Optional<List<WishEntity>> optionalWishes = wishRepository.findAllByUserId(userId);
        ArrayList<Content<?>> contents = new ArrayList<>();
        
        if (optionalWishes.isPresent()){
            List<WishEntity> wishEntities = optionalWishes.get();
            if (!wishEntities.isEmpty()){
                for (WishEntity wishEntity : wishEntities) {
                    CatalogMovie catalogMovie = contentMapper.toCatalogMovie(wishEntity.getCatalogContent(), command.getLocale());
                    contents.add(catalogMovie);
                }
                log.debug("반환할 위시리스트를 추가하였습니다. size: {}", contents.size());
            } else {
                log.debug("위시 리스트가 비어있습니다.");
            }
        } else{
            log.error("위시리스트 가져오는 데에 List 가 null 입니다.");
            throw new InternalServerError();
        }
        return contents;
    }
}
