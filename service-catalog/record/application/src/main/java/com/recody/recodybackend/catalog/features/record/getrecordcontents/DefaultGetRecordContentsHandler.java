package com.recody.recodybackend.catalog.features.record.getrecordcontents;

import com.recody.recodybackend.catalog.data.content.CatalogContentEntity;
import com.recody.recodybackend.catalog.data.record.RecordEntity;
import com.recody.recodybackend.catalog.data.record.RecordMapper;
import com.recody.recodybackend.catalog.data.record.RecordRepository;
import com.recody.recodybackend.record.RecordContent;
import com.recody.recodybackend.record.RecordOrder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
class DefaultGetRecordContentsHandler implements GetRecordContentsHandler<List<RecordContent>>{
    
    private final RecordRepository recordRepository;
    private final RecordMapper recordMapper;
    
    @Override
    @Transactional
    public List<RecordContent> handle(GetRecordContents command) {
        log.debug( "handling command: {}", command );
        Long userId = command.getUserId();
        
        RecordOrder recordOrder = command.getOrder();
        PageRequest pageable = PageRequest.of( command.getPage(),
                                               command.getSize(),
                                               recordOrder.toSort() );
        List<RecordEntity> recordEntities;
        // 유저가 작성한 감상평들을 가져온다.
        Boolean completed = command.getCompleted();
        
        if ( completed == null ) {
            recordEntities = recordRepository.findByUserId( userId, pageable )
                                             .orElseGet( ArrayList::new );
        }
        else {
            recordEntities = recordRepository.findByUserIdAndCompletedOrderByCreatedAtDesc( userId, completed,
                                                                                            pageable )
                                             .orElseGet( ArrayList::new );
        }
        
        
        // 감상평에서 작품정보를 꺼내 정리한다.
        ArrayList<RecordContent> recordContents = new ArrayList<>();
        for (RecordEntity recordEntity : recordEntities) {
            CatalogContentEntity content = recordEntity.getContent();
            RecordContent mapped = recordMapper.map( content, recordEntity, command.getLocale() );
            recordContents.add( mapped );
        }
        
        return recordContents;
    }
}
