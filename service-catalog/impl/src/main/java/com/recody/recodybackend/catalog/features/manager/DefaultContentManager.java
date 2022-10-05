package com.recody.recodybackend.catalog.features.manager;

import com.recody.recodybackend.catalog.data.CatalogContentEntity;
import com.recody.recodybackend.catalog.data.CatalogContentMapper;
import com.recody.recodybackend.catalog.data.CatalogContentRepository;
import com.recody.recodybackend.common.contents.Content;
import com.recody.recodybackend.common.exceptions.UnsupportedCategoryException;
import com.recody.recodybackend.movie.Movie;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
class DefaultContentManager implements ContentManager {
    
    private final CatalogContentRepository contentRepository;
    private final CatalogContentMapper mapper;
    
    @Override
    public <T extends Content> String register(T content) {
        if (content instanceof Movie){
            Movie movie = (Movie) content;
            Optional<CatalogContentEntity> optionalContent = contentRepository.findByContentId(movie.getMovieId());
            if (optionalContent.isPresent()){
                return optionalContent.get().getId();
            }
            CatalogContentEntity contentEntity = mapper.map(movie);
            CatalogContentEntity savedContent = contentRepository.save(contentEntity);
            log.info("Catalog 에 새로운 작품이 등록됩니다. {}", savedContent.getId());
            return savedContent.getId();
        } else {
            log.warn("영화가 아님. content: {}", content);
            throw new UnsupportedCategoryException();
        }
    }
    
//    @Override
//    public Content recognize(String contentId, Category category) {
//        if (category.equals(Category.Movie)) {
//            Optional<CatalogContentEntity> optionalContent = contentRepository.findByContentIdAndCategory(contentId, category);
//            if (optionalContent.isPresent()) {
//                return mapper.mapToMovie(optionalContent.get());
//            }
//        }
//        return null;
//    }
}
