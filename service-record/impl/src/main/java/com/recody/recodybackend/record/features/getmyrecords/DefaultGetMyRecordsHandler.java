package com.recody.recodybackend.record.features.getmyrecords;

import com.recody.recodybackend.common.contents.Category;
import com.recody.recodybackend.common.exceptions.InternalServerError;
import com.recody.recodybackend.record.Record;
import com.recody.recodybackend.record.data.category.EmbeddableCategory;
import com.recody.recodybackend.record.data.record.RecordEntity;
import com.recody.recodybackend.record.data.record.RecordMapper;
import com.recody.recodybackend.record.data.record.RecordRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
class DefaultGetMyRecordsHandler implements GetMyRecordsHandler{
    
    private final RecordRepository recordRepository;
    private final RecordMapper recordMapper;
    
    
    @Override
    @Transactional
    public List<Record> handle(GetMyRecords command) {
        log.debug("handling command: {}", command);
        Long userId = command.getUserId();
        Category category = command.getCategory();
        PageRequest pageable = PageRequest.of(command.getPage(), command.getSize());
        Optional<List<RecordEntity>> optionalRecords;
        String contentId = command.getContentId();
        if (contentId != null){ // content id 값이 있으면 그 작품으로만 쿼리한다.
            optionalRecords = recordRepository.findAllByContentIdAndUserId(userId,
                                                                           contentId,
                                                                           pageable);
        }
        else if (category == null){
            optionalRecords = recordRepository.findAllByUserId(userId);
        }
        else {
            EmbeddableCategory embeddableCategory = new EmbeddableCategory(category.getId(), category.getName());
            optionalRecords = recordRepository.findAllFetchJoinContentWhereCategoryAndUserIdLimit(embeddableCategory,
                                                                                                  userId, pageable);
        }
        
        // repository 는 항상 List 를 반환해야 한다.
        List<RecordEntity> recordEntities = optionalRecords.orElseThrow(InternalServerError::new);
        ArrayList<Record> records = new ArrayList<>();
        for (RecordEntity recordEntity : recordEntities) {
            Record record = recordMapper.map(recordEntity);
            records.add(record);
        }
        log.debug("{} records found", records.size());
        return records;
    }
}
