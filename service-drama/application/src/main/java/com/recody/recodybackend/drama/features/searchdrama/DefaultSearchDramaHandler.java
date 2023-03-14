package com.recody.recodybackend.drama.features.searchdrama;

import com.recody.recodybackend.common.data.QueryMetadata;
import com.recody.recodybackend.common.utils.LanguageUtils;
import com.recody.recodybackend.drama.Dramas;
import com.recody.recodybackend.drama.data.drama.DramaEntity;
import com.recody.recodybackend.drama.data.drama.DramaMapper;
import com.recody.recodybackend.drama.data.drama.DramaRepository;
import com.recody.recodybackend.drama.features.event.DramaEventPublisher;
import com.recody.recodybackend.drama.features.event.DramaQueried;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

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
        Locale language = LanguageUtils.languageOf( query.getKeyword() );
        Page<DramaEntity> dramaEntitiesPage =
                dramaRepository.findPagedByTitleLikeFilterByGenres( query.getKeyword(),
                                                                    language,
                                                                    PageRequest.of( query.getPage() - 1, 10 ),
                                                                    query.getGenreIds() );
        
        dramaEventPublisher.publish( DramaQueried.builder()
                                                 .keyword( query.getKeyword() )
                                                 .locale( query.getLocale() )
                                                 .build() );
        QueryMetadata queryMetadata = new QueryMetadata( dramaEntitiesPage );
        log.debug( "{} 개의 결과를 검색하였습니다.", queryMetadata.getSize() );
        return Dramas.of( dramaMapper.map( dramaEntitiesPage.getContent(), query.getLocale() ),
                          queryMetadata );
    }
}
