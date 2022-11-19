package com.recody.recodybackend.catalog.features.genre.addcustomgenre;

import com.recody.recodybackend.catalog.data.category.CategoryEntity;
import com.recody.recodybackend.catalog.data.category.CategoryRepository;
import com.recody.recodybackend.catalog.data.genre.CatalogGenreEntity;
import com.recody.recodybackend.catalog.data.genre.CatalogGenreMapper;
import com.recody.recodybackend.catalog.data.genre.CatalogGenreRepository;
import com.recody.recodybackend.catalog.data.user.CatalogUserEntity;
import com.recody.recodybackend.catalog.data.user.CatalogUserRepository;
import com.recody.recodybackend.common.exceptions.ApplicationExceptions;
import com.recody.recodybackend.exceptions.CatalogErrorType;
import com.recody.recodybackend.exceptions.CategoryNotFoundException;
import com.recody.recodybackend.genre.CustomGenre;
import com.recody.recodybackend.users.exceptions.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
class DefaultAddCustomGenreHandler implements AddCustomGenreHandler {
    
    private final CatalogGenreRepository genreRepository;
    
    private final CatalogGenreMapper genreMapper;
    private final CategoryRepository categoryRepository;
    private final CatalogUserRepository userRepository;
    
    @Override
    public CustomGenre handle(AddCustomGenre command) {
        log.debug( "handling command: {}", command );
        Long userId = command.getUserId();
        String categoryId = command.getCategoryId().getValue();
        String genreName = command.getGenreName().getValue();
        String genreIconUrl = command.getGenreIconUrl().getValue();
        CatalogUserEntity catalogUserEntity = userRepository.findById( userId ).orElseThrow( UserNotFoundException::new );
        CategoryEntity categoryEntity = categoryRepository.findById( categoryId ).orElseThrow( CategoryNotFoundException::new );
        Optional<CatalogGenreEntity> optionalGenre = genreRepository.findByUserAndName( catalogUserEntity, genreName );
        
        if ( optionalGenre.isPresent() ) {
            log.debug( "이 유저는 이미 같은 이름의 장르를 생성하였습니다." );
            throw ApplicationExceptions.badRequestOf( CatalogErrorType.GenreNameAlreadyExists );
        }
        
        CatalogGenreEntity newGenreEntity = CatalogGenreEntity.builder()
                                                              .name( genreName )
                                                              .iconUrl( genreIconUrl )
                                                              .user( catalogUserEntity )
                                                              .category( categoryEntity )
                                                              .build();
        
        CatalogGenreEntity savedGenreEntity = genreRepository.save( newGenreEntity );
        CustomGenre customGenre = genreMapper.toCustomGenre( savedGenreEntity );
        log.info( "새로운 커스텀 장르를 생성하였습니다.: {}", customGenre );
        return customGenre;
    }
}
