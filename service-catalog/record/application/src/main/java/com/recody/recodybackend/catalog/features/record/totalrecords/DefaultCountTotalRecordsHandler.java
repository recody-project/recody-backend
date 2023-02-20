package com.recody.recodybackend.catalog.features.record.totalrecords;

import com.recody.recodybackend.Monthly;
import com.recody.recodybackend.catalog.data.record.RecordRepository;
import com.recody.recodybackend.record.RecordCount;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
class DefaultCountTotalRecordsHandler implements CountTotalRecordsHandler {
    
    private final RecordRepository recordRepository;
    
    /**
     * 감상평의 개수를 구한다.
     * - 주어진 Monthly 객체가 가진 날짜를 이용한다.
     */
    @Override
    public RecordCount handle(CountTotalRecords command) {
        log.debug( "handling command: {}", command );
        Long userId = command.getUserId();
        Monthly monthly = command.getMonthly();
        Integer countValue;
        countValue
                = recordRepository.countByUserIdAndAppreciationDateAfter( userId, monthly.getDateOfFirstDay() );
        log.info( "유저의 감상평 개수를 반환합니다.: {}", countValue );
        return RecordCount.of( countValue );
    }
}
