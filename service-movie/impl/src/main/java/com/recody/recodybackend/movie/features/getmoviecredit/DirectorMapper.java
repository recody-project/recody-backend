package com.recody.recodybackend.movie.features.getmoviecredit;

import com.recody.recodybackend.movie.data.people.MoviePersonRepository;
import com.recody.recodybackend.movie.features.getmoviecredit.dto.TMDBCrew;
import com.recody.recodybackend.movie.features.tmdb.TMDB;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@Mapper(componentModel = "spring", imports = {
        TMDB.class
})
@Slf4j
abstract class DirectorMapper {
    
    @Autowired
    private MoviePersonRepository personRepository;
    
    @Mapping(target = "id", source = "crew.id")
    @Mapping(target = "tmdbId", source = "crew.id")
    @Mapping(target = "profilePath", expression = "java(TMDB.fullPosterPath(crew.getProfilePath()))")
    public abstract Director map(TMDBCrew crew);
    
    
    /**
     * 고유 ID 를 부여한다.
     * 쿼리 성능 상 일단은 ID는 사용하지 않게 함.
     * @param tmdbId tmdb 에서 주는 사람에 대한 id
     * @return 데이터베이스에서 주입한 id
     * @author motive
     */
    @Named("setIdIfExists")
    public Long setId(Integer tmdbId){
        Optional<Long> optionalId = personRepository.findIdByTmdbId(tmdbId);
        if (optionalId.isEmpty()) {
            return null;
        } else {
            Long id = optionalId.get();
            log.debug("found person id: {}", id);
            return id;
        }
    }
}
