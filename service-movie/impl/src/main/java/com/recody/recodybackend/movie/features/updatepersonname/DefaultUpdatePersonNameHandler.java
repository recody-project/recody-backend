package com.recody.recodybackend.movie.features.updatepersonname;

import com.recody.recodybackend.movie.TMDBPersonName;
import com.recody.recodybackend.movie.data.people.MoviePersonEntity;
import com.recody.recodybackend.movie.data.people.MoviePersonRepository;
import com.recody.recodybackend.movie.features.fetchpersonname.FetchPersonName;
import com.recody.recodybackend.movie.features.fetchpersonname.FetchPersonNameHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
class DefaultUpdatePersonNameHandler implements UpdatePersonNameHandler<Void>{
    
    private final FetchPersonNameHandler<TMDBPersonName> fetchPersonNameHandler;
    
    private final MoviePersonRepository personRepository;
    
    @Override
    @Transactional
    public Void handle(UpdateKoreanPersonName command) {
        log.debug( "handling command: {}", command );
        Long personNameId = command.getPersonNameId();
        Optional<MoviePersonEntity> optionalPerson = personRepository.findByName_Id( personNameId );
        if (optionalPerson.isEmpty()){
            log.error( "사람 이름을 업데이트하는 도중 사람 데이터를 찾지 못했습니다." );
            return null;
        }
        MoviePersonEntity moviePersonEntity = optionalPerson.get();
        Integer tmdbPersonId = moviePersonEntity.getTmdbId();
        TMDBPersonName name = fetchPersonNameHandler.handle( FetchPersonName.builder().tmdbPersonId( tmdbPersonId ).build() );
        moviePersonEntity.getName().setKoreanName( name.getValue() );
        log.info( "사람 이름을 업데이트 하였습니다. name: {}", name );
        return null;
    }
}
