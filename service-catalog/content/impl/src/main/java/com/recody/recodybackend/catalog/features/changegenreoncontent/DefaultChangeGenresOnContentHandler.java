package com.recody.recodybackend.catalog.features.changegenreoncontent;

import com.recody.recodybackend.catalog.data.content.CatalogContentEntity;
import com.recody.recodybackend.catalog.data.content.CatalogContentRepository;
import com.recody.recodybackend.catalog.data.genre.CatalogGenreEntity;
import com.recody.recodybackend.catalog.data.genre.CatalogGenreRepository;
import com.recody.recodybackend.catalog.data.genre.PersonalizedGenreEntity;
import com.recody.recodybackend.catalog.data.genre.PersonalizedGenreRepository;
import com.recody.recodybackend.catalog.data.user.CatalogUserEntity;
import com.recody.recodybackend.catalog.data.user.CatalogUserRepository;
import com.recody.recodybackend.event.GenrePersonalized;
import com.recody.recodybackend.exceptions.ContentNotFoundException;
import com.recody.recodybackend.genre.GenreId;
import com.recody.recodybackend.genre.GenreIds;
import com.recody.recodybackend.movie.exceptions.GenreNotFoundException;
import com.recody.recodybackend.users.exceptions.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
class DefaultChangeGenresOnContentHandler implements ChangeGenresOnContentHandler {
    private final CatalogUserRepository userRepository;
    private final CatalogContentRepository contentRepository;
    private final CatalogGenreRepository genreRepository;
    private final PersonalizedGenreRepository personalizedGenreRepository;
    
    @Override
    public GenrePersonalized handle(ChangeGenresOnContent command) {
        log.debug( "handling command: {}", command );
        Long userId = command.getUserId();
        String contentId = command.getContentId().getContentId();
        
        CatalogUserEntity userEntity = userRepository.findById( userId ).orElseThrow( UserNotFoundException::new );
        CatalogContentEntity contentEntity = contentRepository.findByContentId( contentId ).orElseThrow( ContentNotFoundException::new );
        List<PersonalizedGenreEntity> personalizedGenres = personalizedGenreRepository.findAllByContentAndUser( contentEntity, userEntity );
        log.debug( "personalizedGenres.size(): {}", personalizedGenres.size() );
        
        personalizedGenreRepository.deleteAllInBatch(personalizedGenres);
        
        GenreIds genreIds = command.getGenreIds();
    
        for (GenreId genreIdObject : genreIds) {
            String genreId = genreIdObject.getValue();
            CatalogGenreEntity genreEntity = genreRepository.findByIdAndUser( genreId, userEntity ).orElseThrow( GenreNotFoundException::new );
            PersonalizedGenreEntity newPersonalizedGenreEntity = PersonalizedGenreEntity.builder()
                                                                                        .genre( genreEntity )
                                                                                        .content( contentEntity )
                                                                                        .user( userEntity )
                                                                                        .build();
            PersonalizedGenreEntity savedPersonalizedGenreEntity = personalizedGenreRepository.save( newPersonalizedGenreEntity );
        }
        
        GenrePersonalized event = GenrePersonalized.builder()
                                                   .genreIds( genreIds )
                                                   .contentId( command.getContentId() )
                                                   .userId( userId ).build();
        log.info( "장르 설정 완료" );
        return event;
    }
}
