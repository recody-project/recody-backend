package com.recody.recodybackend.drama.features.searchdrama;

import com.recody.recodybackend.drama.Dramas;
import com.recody.recodybackend.drama.data.drama.DramaEntity;
import com.recody.recodybackend.drama.data.drama.DramaMapper;
import com.recody.recodybackend.drama.data.drama.DramaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
class DefaultSearchDramaHandler implements SearchDramaHandler<Dramas> {
    
    private final DramaRepository dramaRepository;
    private final DramaMapper dramaMapper;
    
    @Override
    public Dramas handle(SearchDramas query) {
        log.debug( "handling query: {}", query );
        List<DramaEntity> dramaEntities =
                dramaRepository.findByTitleLike( query.getKeyword(),
                                                 query.getLanguage(),
                                                 Pageable.unpaged() );
        
        return Dramas.of( dramaMapper.map( dramaEntities, query.getLanguage() ));
    }
}
