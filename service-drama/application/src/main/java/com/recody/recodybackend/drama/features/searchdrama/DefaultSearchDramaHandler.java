package com.recody.recodybackend.drama.features.searchdrama;

import com.recody.recodybackend.common.utils.LanguageUtils;
import com.recody.recodybackend.drama.Dramas;
import com.recody.recodybackend.drama.data.drama.DramaEntity;
import com.recody.recodybackend.drama.data.drama.DramaMapper;
import com.recody.recodybackend.drama.data.drama.DramaRepository;
import com.recody.recodybackend.drama.features.event.DramaEventPublisher;
import com.recody.recodybackend.drama.features.event.EmptyDramaQueried;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Locale;

@Component
@RequiredArgsConstructor
@Slf4j
class DefaultSearchDramaHandler implements SearchDramaHandler<Dramas> {
    
    private final DramaRepository dramaRepository;
    private final DramaMapper dramaMapper;
    private final DramaEventPublisher dramaEventPublisher;
    
    @Override
    public Dramas handle(SearchDramas query) {
        log.debug( "handling query: {}", query );
        // language: 검색 결과의 언어
        Locale language = LanguageUtils.languageOf( query.getKeyword() );
        List<DramaEntity> dramaEntities =
                dramaRepository.findByTitleLike( query.getKeyword(),
                                                 language,
                                                 Pageable.unpaged() );
        if ( dramaEntities.isEmpty() ) {
            dramaEventPublisher.publish( EmptyDramaQueried.builder()
                                                          .keyword( query.getKeyword() )
                                                          .locale( query.getLocale() )
                                                          .build() );
        }
        return Dramas.of( dramaMapper.map( dramaEntities, query.getLocale() ) );
    }
}
