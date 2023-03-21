package com.recody.recodybackend.insight.features.getusersrecordcount;

import com.recody.recodybackend.Monthly;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@Slf4j
class DefaultGetUsersRecordCountHandler implements GetUsersRecordCountHandler<InsightRecordCount> {
    
    private final WebClient insightWebClient;
//    private GetUsersRecordCountClient getUsersRecordCountClient;
    
    public DefaultGetUsersRecordCountHandler( @Qualifier( "insightWebClient" ) WebClient insightWebClient) {
        this.insightWebClient = insightWebClient;
    }
    
    @Override
    public InsightRecordCount handle(GetUsersRecordCount command) {
        Long userId = command.getUserId();
        Monthly monthly = command.getMonthly();
        return insightWebClient.get()
                               .uri( uriBuilder -> uriBuilder
                                                          .path( "/api/v1/record/records/total-data" )
                                                          .queryParam( "yearMonth", monthly.asString() )
                                                           .queryParam( "userId", userId )
                                                          .build() )
                               .retrieve()
                               .bodyToMono( InsightRecordCount.class )
                               .block();
    }
}
