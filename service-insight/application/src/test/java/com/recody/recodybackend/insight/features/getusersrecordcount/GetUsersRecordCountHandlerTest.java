package com.recody.recodybackend.insight.features.getusersrecordcount;

import com.recody.recodybackend.Monthly;
import com.recody.recodybackend.insight.InsightApplication;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ActiveProfiles("test")
@ContextConfiguration(classes = InsightApplication.class)
class GetUsersRecordCountHandlerTest {
   
    @Autowired
    GetUsersRecordCountHandler<InsightRecordCount> getUsersRecordCountHandler;
    
    
    @Test
    @DisplayName( "감상평의 개수를 가져온다." )
    void test01() {
        // given
        long userId = 2L;
        InsightRecordCount count = getUsersRecordCountHandler.handle(
                GetUsersRecordCount.builder()
                                   .userId( userId )
                                   .monthly( Monthly.of( "2022-01" ) )
                                   .build() );
        // when
        System.out.println( "count = " + count );
        // then
    }
}