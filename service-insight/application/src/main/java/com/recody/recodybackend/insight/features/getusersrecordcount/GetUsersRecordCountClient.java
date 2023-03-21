package com.recody.recodybackend.insight.features.getusersrecordcount;

import com.recody.recodybackend.insight.HeaderConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient( name = "getUserRecordCountHandler",
              url = "${insight.record.base-url}",
              configuration = HeaderConfiguration.class )
public interface GetUsersRecordCountClient {
    
    @GetMapping( "/api/v1/record/records/total-data" )
    InsightRecordCount handle(@RequestParam( value = "yearMonth" ) String yearMonth,
                              @RequestParam Long userId
                              );
}
