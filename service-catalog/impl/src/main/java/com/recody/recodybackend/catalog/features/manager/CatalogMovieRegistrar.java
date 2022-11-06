package com.recody.recodybackend.catalog.features.manager;

import com.recody.recodybackend.catalog.CatalogMovieDetail;
import com.recody.recodybackend.catalog.data.content.CatalogContentEntity;
import com.recody.recodybackend.catalog.data.content.CatalogContentMapper;
import com.recody.recodybackend.catalog.data.content.CatalogContentRepository;
import com.recody.recodybackend.common.contents.register.AsyncContentRegistrar;
import com.recody.recodybackend.movie.MovieDetail;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
class CatalogMovieRegistrar implements AsyncContentRegistrar<CatalogMovieDetail, MovieDetail> {
    
    private final CatalogContentRepository contentRepository;
    private final CatalogContentMapper mapper;
    
    // TODO: 카탈로그 서비스에서 엔티티는 데이터를 구분하는 용도로만?
    @Override
    public CatalogMovieDetail register(MovieDetail movieDetail, Locale locale) {
        log.debug("registering content: {}", movieDetail);

        Optional<CatalogContentEntity> optionalContent =
                contentRepository.findByContentId(movieDetail.getContentId());
        if (optionalContent.isPresent()){
            CatalogContentEntity entity = optionalContent.get();
            CatalogMovieDetail catalogMovieDetail = mapper.toCatalogMovieDetail(entity, movieDetail);
            log.info("Catalog 작품을 업데이트하고 반환합니다. {}", catalogMovieDetail.getContentId());
            return catalogMovieDetail;
        }
        
        CatalogContentEntity contentEntity = mapper.newEntity(movieDetail, locale);
        CatalogContentEntity savedContentEntity = contentRepository.save(contentEntity);
        CatalogMovieDetail catalogMovieDetail = mapper.toCatalogMovieDetail(savedContentEntity, movieDetail);
        log.info("Catalog 에 새로운 작품이 등록됩니다. {}", catalogMovieDetail.getContentId());
        return catalogMovieDetail;
    }
}
