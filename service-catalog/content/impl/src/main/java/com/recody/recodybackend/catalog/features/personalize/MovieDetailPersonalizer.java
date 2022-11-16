package com.recody.recodybackend.catalog.features.personalize;

import com.recody.recodybackend.catalog.data.category.CategoryEntity;
import com.recody.recodybackend.catalog.data.category.CategoryMapper;
import com.recody.recodybackend.catalog.data.category.CategoryRepository;
import com.recody.recodybackend.catalog.data.content.CatalogContentEntity;
import com.recody.recodybackend.catalog.data.content.CatalogContentRepository;
import com.recody.recodybackend.catalog.data.genre.CatalogGenreMapper;
import com.recody.recodybackend.catalog.data.genre.PersonalizedGenreEntity;
import com.recody.recodybackend.catalog.data.genre.PersonalizedGenreRepository;
import com.recody.recodybackend.catalog.data.user.CatalogUserEntity;
import com.recody.recodybackend.catalog.data.user.CatalogUserRepository;
import com.recody.recodybackend.category.CustomCategory;
import com.recody.recodybackend.common.contents.BasicCategory;
import com.recody.recodybackend.common.contents.Category;
import com.recody.recodybackend.common.contents.Genre;
import com.recody.recodybackend.common.exceptions.ContentNotFoundException;
import com.recody.recodybackend.content.CatalogMovieDetail;
import com.recody.recodybackend.content.PersonalizedMovieDetail;
import com.recody.recodybackend.movie.MovieGenre;
import com.recody.recodybackend.users.exceptions.UserNotFoundException;
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
    
    private final PersonalizedGenreRepository personalizedGenreRepository;
    
    private final CatalogUserRepository userRepository;
    
    private final CatalogGenreMapper genreMapper;
    
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
        genres = resolvePersonalizedGenresOrGetDefault( defaultGenres, userId, contentEntity );
        
        PersonalizedMovieDetail personalizedMovieDetail
                = personalizedMovieMapper.map( content, category, genres, userId );
        
        log.info( "영화 정보 개인화 완료" );
        return personalizedMovieDetail;
    }
    
    private Category resolvePersonalizedCategoryOrGetDefault(BasicCategory defaultCategory, Long userId,
                                                             CatalogContentEntity contentEntity) {
        Optional<CategoryEntity> optionalCategory
                = categoryRepository.findByUserIdAndContentFromJoinedPersonalizedCategory( userId, contentEntity );
        
        if ( optionalCategory.isPresent() ) {
            CategoryEntity categoryEntity = optionalCategory.get();
            CustomCategory customCategory = categoryMapper.toCustomCategory( categoryEntity );
            log.debug( "this user({}) have customCategory({}) on this content({})", userId, customCategory, contentEntity.getId() );
            return customCategory;
        }
        else {
            log.debug(
                    "this user({}) have NO customCategory on this content({}) so setting default({})", userId, contentEntity.getId(),
                    defaultCategory );
            return defaultCategory;
        }
    }
    
    private List<Genre> resolvePersonalizedGenresOrGetDefault(List<MovieGenre> movieGenres, Long userId,
                                                              CatalogContentEntity contentEntity) {
        CatalogUserEntity userEntity = userRepository.findById( userId ).orElseThrow( UserNotFoundException::new );
        List<PersonalizedGenreEntity> personalizedGenres = personalizedGenreRepository.findAllByContentAndUser(
                contentEntity, userEntity );
        if ( personalizedGenres.isEmpty() ) {
            log.debug( "기본 장르를 반환 " );
            return List.copyOf( movieGenres );
        }
        List<Genre> genres = genreMapper.mapGenres( personalizedGenres );
        log.debug( "개인화된 장르들을 반환함." );
        return genres;
    }
}
