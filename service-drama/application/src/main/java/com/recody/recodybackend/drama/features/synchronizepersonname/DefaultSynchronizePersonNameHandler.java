package com.recody.recodybackend.drama.features.synchronizepersonname;

import com.recody.recodybackend.drama.PersonId;
import com.recody.recodybackend.drama.data.people.DramaPersonEntity;
import com.recody.recodybackend.drama.data.people.DramaPersonRepository;
import com.recody.recodybackend.drama.features.fetchpersondetail.FetchDramaPersonDetail;
import com.recody.recodybackend.drama.features.fetchpersondetail.FetchDramaPersonDetailHandler;
import com.recody.recodybackend.drama.tmdb.TMDBGetPersonDetailResponse;
import com.recody.recodybackend.drama.tmdb.TMDBPersonName;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
class DefaultSynchronizePersonNameHandler implements SynchronizePersonNameHandler<Void> {
    
    private final FetchDramaPersonDetailHandler<TMDBGetPersonDetailResponse> fetchDramaPersonDetailHandler;
    
    private final DramaPersonRepository personRepository;
    
    @Override
    @Transactional
    public Void handle(PersonId personId) {
        Long personIdValue = personId.getValue();
        Optional<DramaPersonEntity> optionalPerson = personRepository.findById( personIdValue );
        if ( optionalPerson.isEmpty() ) {
            log.error( "Person 을 찾을 수 없습니다.: {}", optionalPerson );
            return null;
        }
        DramaPersonEntity dramaPerson = optionalPerson.get();
        
        Integer tmdbPersonId = dramaPerson.getTmdbId();
        TMDBGetPersonDetailResponse personDetailResponse =
                fetchDramaPersonDetailHandler.handle( new FetchDramaPersonDetail( tmdbPersonId ) );
        
        String defaultName = personDetailResponse.getName();
        List<String> alsoKnownAs = personDetailResponse.getAlsoKnownAs();
        dramaPerson.getName().setOriginalName( defaultName );
        dramaPerson.getName()
                   .setKoreanName( TMDBPersonName.firstKoreanNameOf( alsoKnownAs, defaultName )
                                                 .getValue() );
        dramaPerson.getName()
                   .setEnglishName( TMDBPersonName.firstEnglishNameOf( alsoKnownAs, defaultName )
                                                  .getValue() );
        return null;
    }
}
