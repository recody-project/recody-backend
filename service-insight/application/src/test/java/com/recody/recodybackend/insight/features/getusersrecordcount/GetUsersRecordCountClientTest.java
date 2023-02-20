package com.recody.recodybackend.insight.features.getusersrecordcount;

import com.recody.recodybackend.Monthly;
import com.recody.recodybackend.insight.InsightApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
@SpringBootTest
@ActiveProfiles("test")
@ContextConfiguration(classes = InsightApplication.class)
class GetUsersRecordCountClientTest {
    
    @Autowired
    GetUsersRecordCountClient getUsersRecordCountClient;
    private Long USER_ID = 2L;
    @Test
    void test01() {
        // given
        System.out.println(Monthly.thisMonth().asString());
        InsightRecordCount handle = getUsersRecordCountClient.handle( Monthly.thisMonth().asString(), USER_ID);
        // when
        System.out.println( "handle = " + handle );
        
        
        // then
    }
    
}