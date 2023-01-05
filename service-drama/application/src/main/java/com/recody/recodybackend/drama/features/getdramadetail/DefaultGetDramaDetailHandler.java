package com.recody.recodybackend.drama.features.getdramadetail;

import com.recody.recodybackend.common.exceptions.ContentNotFoundException;
import com.recody.recodybackend.drama.DramaDetail;
import com.recody.recodybackend.drama.data.drama.DramaEntity;
import com.recody.recodybackend.drama.data.drama.DramaMapper;
import com.recody.recodybackend.drama.data.drama.DramaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
class DefaultGetDramaDetailHandler implements GetDramaDetailHandler<DramaDetail> {
    
    private final DramaRepository dramaRepository;
    
    private final DramaMapper dramaMapper;
    
    @Override
    public DramaDetail handle(GetDramaDetail query) {
        log.trace( "handling query: {}", query );
        String dramaId = query.getDramaId();
        DramaEntity dramaEntity = dramaRepository.findById( dramaId )
                                                 .orElseThrow( ContentNotFoundException::new );
        DramaDetail dramaDetail = dramaMapper.toDramaDetail( dramaEntity, query.getLocale() );
        log.debug( "dramaDetail 을 반환합니다.: {}", dramaDetail );
        return dramaDetail;
    }
}
