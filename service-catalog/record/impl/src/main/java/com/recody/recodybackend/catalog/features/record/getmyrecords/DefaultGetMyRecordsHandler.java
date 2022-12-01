package com.recody.recodybackend.catalog.features.record.getmyrecords;

import com.recody.recodybackend.catalog.data.category.CategoryEntity;
import com.recody.recodybackend.catalog.data.record.RecordEntity;
import com.recody.recodybackend.catalog.data.record.RecordMapper;
import com.recody.recodybackend.catalog.data.record.RecordRepository;
import com.recody.recodybackend.common.contents.Category;
import com.recody.recodybackend.common.data.QueryResult;
import com.recody.recodybackend.common.exceptions.InternalServerError;
import com.recody.recodybackend.record.Record;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
class DefaultGetMyRecordsHandler implements GetMyRecordsHandler {
    
    private final RecordRepository recordRepository;
    private final RecordMapper recordMapper;
    
    
    @Override
    @Transactional
    public List<Record> handle(GetMyRecords command) {
        log.debug( "handling command: {}", command );
        Long userId = command.getUserId();
        Category category = command.getCategory();
        PageRequest pageable = PageRequest.of( command.getPage() - 1, command.getSize() );
        Optional<List<RecordEntity>> optionalRecords;
        String contentId = command.getContentId();
        if ( contentId != null ) { // content id 값이 있으면 그 작품으로만 쿼리한다.
            optionalRecords = recordRepository.findAllByContentIdAndUserId(
                    userId,
                    contentId,
                    pageable );
        }
        else if ( category == null ) {
            optionalRecords = recordRepository.findAllByUserId( userId );
        }
        else {
            CategoryEntity embeddableCategory = CategoryEntity.builder().id( category.getId() ).name( category.getName() ).build();
            optionalRecords = recordRepository.findAllFetchJoinContentWhereCategoryAndUserIdLimit( embeddableCategory,
                                                                                                   userId, pageable );
        }
        
        // repository 는 항상 List 를 반환해야 한다.
        List<RecordEntity> recordEntities = optionalRecords.orElseThrow( InternalServerError::new );
        ArrayList<Record> records = new ArrayList<>();
        for (RecordEntity recordEntity : recordEntities) {
            Record record = recordMapper.map( recordEntity );
            records.add( record );
        }
        log.debug( "{} records found", records.size() );
        return records;
    }
    
    @Override
    public QueryResult<Record> handleQuery(GetMyRecords command) {
        log.debug( "handling command: {}", command );
        Long userId = command.getUserId();
        Category category = command.getCategory();
        PageRequest pageable = PageRequest.of( command.getPage() - 1, command.getSize() );
        Page<RecordEntity> recordEntitiesPage;
        String contentId = command.getContentId();
        
        if ( contentId != null ) { // content id 값이 있으면 그 작품으로만 쿼리한다.
            recordEntitiesPage = recordRepository.findAllByContentIdAndUserIdPage(
                    userId,
                    contentId,
                    pageable );
            return new QueryResult<>(
                    recordEntitiesPage, recordMapper.map( recordEntitiesPage.getContent() ) );
        }
        else if ( category == null ) {
            recordEntitiesPage = recordRepository.findAllByUserId( userId, pageable );
            return new QueryResult<>(
                    recordEntitiesPage, recordMapper.map( recordEntitiesPage.getContent() )
            );
            
        }
        else {
            CategoryEntity categoryEntity = CategoryEntity.builder().id( category.getId() ).name( category.getName() ).build();
            recordEntitiesPage = recordRepository.findAllFetchJoinContentWhereCategoryAndUserIdLimitPage( categoryEntity,
                                                                                                          userId, pageable );
            return new QueryResult<>( recordEntitiesPage, recordMapper.map( recordEntitiesPage.getContent() ) );
        }
    }
}
