package com.recody.recodybackend.catalog.features.record.totalrecords;

import com.recody.recodybackend.catalog.data.record.RecordRepository;
import com.recody.recodybackend.record.RecordCount;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
@Slf4j
class DefaultCountTotalRecordsHandler implements CountTotalRecordsHandler {
    
    private final RecordRepository recordRepository;
    
    @Override
    public RecordCount handle(CountTotalRecords command) {
        log.debug( "handling command: {}", command );
        Long userId = command.getUserId();
        Integer countValue;
        if (command.isThisMonth()){
            countValue
                    = recordRepository.countByUserIdAndAppreciationDateAfter( userId, LocalDate.now().minusMonths( 1L ) );
        }
        else {
            countValue = recordRepository.countByUserId( userId );
        }
        log.info( "유저의 감상평 개수를 반환합니다.: {}", countValue );
        return RecordCount.of( countValue );
    }
}
