package com.recody.recodybackend.record.features.deleterecord;

import com.recody.recodybackend.record.data.record.RecordEntity;
import com.recody.recodybackend.record.data.record.RecordRepository;
import com.recody.recodybackend.record.exceptions.FailedToRemoveRecordException;
import com.recody.recodybackend.record.exceptions.RecordNotFound;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
class DefaultDeleteRecordHandler implements DeleteRecordHandler{
    
    private final RecordRepository recordRepository;
    
    @Override
    @Transactional
    public LocalDateTime handle(DeleteRecord command) {
        String recordId = command.getRecordId();
        Long userId = command.getUserId();
        log.debug("handling deleting recordId: {}", recordId);
        Optional<RecordEntity> optionalRecord = recordRepository.findByRecordId(recordId);
        if (optionalRecord.isEmpty()) {
            throw new RecordNotFound();
        }
        RecordEntity recordEntity = optionalRecord.get();
        if (!recordEntity.getUserId().equals(userId)){
            throw new UserDoesNotOwnTheRecordException();
        }
        try {
            recordEntity.delete();
            log.info("감상평 삭제 처리됨. recordId: {}", recordId);
        } catch (DataAccessException exception){
            log.error("Record 삭제에 실패하였습니다. exception: {}, recordId: {}", exception.getMessage(), recordId);
            throw new FailedToRemoveRecordException();
        }
        return recordEntity.getDeletedAt();
    }
}
