package com.recody.recodybackend.record.features.getrecordcontents;

import com.recody.recodybackend.record.RecordContent;
import com.recody.recodybackend.record.data.content.RecordContentEntity;
import com.recody.recodybackend.record.data.content.RecordContentMapper;
import com.recody.recodybackend.record.data.record.RecordEntity;
import com.recody.recodybackend.record.data.record.RecordRepository;
import com.recody.recodybackend.record.exceptions.RecordNotFound;
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
        List<RecordEntity> recordEntities = recordRepository.findByUserIdOrderByCreatedAtDesc(userId, pageable)
                                                            .orElseThrow(RecordNotFound::new);
    
        ArrayList<RecordContent> recordContents = new ArrayList<>();
        for (RecordEntity recordEntity : recordEntities) {
            RecordContentEntity content = recordEntity.getContent();
            LocalDate appreciationDate = recordEntity.getAppreciationDate();
            RecordContent mapped = contentMapper.map(content, appreciationDate);
            recordContents.add(mapped);
        }
        
        return recordContents;
    }
}
