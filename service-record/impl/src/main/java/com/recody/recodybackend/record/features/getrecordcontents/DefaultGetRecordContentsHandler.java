package com.recody.recodybackend.record.features.getrecordcontents;

import com.recody.recodybackend.record.RecordContent;
import com.recody.recodybackend.record.data.content.RecordContentEntity;
import com.recody.recodybackend.record.data.content.RecordContentMapper;
import com.recody.recodybackend.record.data.record.RecordEntity;
import com.recody.recodybackend.record.data.record.RecordRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
class DefaultGetRecordContentsHandler implements GetRecordContentsHandler{
    
    private final RecordRepository recordRepository;
    private final RecordContentMapper contentMapper;
    
    @Override
    @Transactional
    public List<RecordContent> handle(GetRecordContents command) {
        log.debug("handling command: {}", command);
        Long userId = command.getUserId();
        
        PageRequest pageable = PageRequest.of(command.getPage(), command.getSize());
        List<RecordEntity> recordEntities;
        // 유저가 작성한 감상평들을 가져온다.
        Boolean completed = command.getCompleted();
        
        if ( completed == null ){
            recordEntities = recordRepository.findByUserIdOrderByCreatedAtDesc(userId, pageable)
                                             .orElseGet( ArrayList::new );
        }
        else {
            recordEntities = recordRepository.findByUserIdAndCompletedOrderByCreatedAtDesc( userId, completed, pageable )
                                     .orElseGet( ArrayList::new );
        }
        
        
        // 감상평에서 작품정보를 꺼내 정리한다.
        ArrayList<RecordContent> recordContents = new ArrayList<>();
        for (RecordEntity recordEntity : recordEntities) {
            RecordContentEntity content = recordEntity.getContent();
            LocalDate appreciationDate = recordEntity.getAppreciationDate();
            RecordContent mapped = contentMapper.map(content, appreciationDate, command.getLocale());
            recordContents.add(mapped);
        }
        
        return recordContents;
    }
}
