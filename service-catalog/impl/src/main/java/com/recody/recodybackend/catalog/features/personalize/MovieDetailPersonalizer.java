package com.recody.recodybackend.catalog.features.personalize;

import com.recody.recodybackend.catalog.CustomCategory;
import com.recody.recodybackend.catalog.PersonalizedMovieDetail;
import com.recody.recodybackend.catalog.CatalogMovieDetail;
import com.recody.recodybackend.catalog.data.category.*;
import com.recody.recodybackend.catalog.data.content.CatalogContentEntity;
import com.recody.recodybackend.catalog.data.content.CatalogContentRepository;
import com.recody.recodybackend.common.contents.BasicCategory;
import com.recody.recodybackend.common.contents.Category;
import com.recody.recodybackend.common.contents.Genre;
import com.recody.recodybackend.common.exceptions.ContentNotFoundException;
import com.recody.recodybackend.movie.MovieGenre;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
class MovieDetailPersonalizer
        implements ContentDetailPersonalizer<CatalogMovieDetail, PersonalizedMovieDetail> {
    
    private final PersonalizedMovieMapper personalizedMovieMapper;
    private final CatalogContentRepository contentRepository;
    
    private final CategoryRepository categoryRepository;
    
    private final CategoryMapper categoryMapper;
    
    @Override
    @Transactional
    public PersonalizedMovieDetail personalize(CatalogMovieDetail content, Long userId) {
    
        String contentId = content.getContentId();
        CatalogContentEntity contentEntity = contentRepository.findByContentId( contentId )
                                                              .orElseThrow( ContentNotFoundException::new );
    
        Category category;
        List<Genre> genres;
        
        BasicCategory defaultCategory = content.getCategory();
        List<MovieGenre> defaultGenres = content.getGenres();
        
        category = resolvePersonalizedCategoryOrGetDefault( defaultCategory, userId, contentEntity );
        genres = resolvePersonalizedGenresOrGetDefault( defaultGenres );
    
        PersonalizedMovieDetail personalizedMovieDetail
                = personalizedMovieMapper.map(content, category, genres, userId);
        
        log.info("영화 정보 개인화 완료");
        return personalizedMovieDetail;
    }
    
    private Category resolvePersonalizedCategoryOrGetDefault(BasicCategory defaultCategory, Long userId, CatalogContentEntity contentEntity) {
        Optional<CategoryEntity> optionalCategory
                = categoryRepository.findByUserIdAndContentFromJoinedPersonalizedCategory( userId, contentEntity );
        
        if ( optionalCategory.isPresent() ){
            CategoryEntity categoryEntity = optionalCategory.get();
            CustomCategory customCategory = categoryMapper.toCustomCategory( categoryEntity );
            log.debug( "this user({}) have customCategory({}) on this content({})", userId, customCategory, contentEntity.getId() );
            return customCategory;
        }
        else {
            log.debug( "this user({}) have NO customCategory on this content({}) so setting default({})", userId, contentEntity.getId(), defaultCategory );
            return defaultCategory;
        }
    }
    
    private List<Genre> resolvePersonalizedGenresOrGetDefault(List<MovieGenre> movieGenres) {
        // 구현되지 않음.
        return List.copyOf( movieGenres );
    }
}
