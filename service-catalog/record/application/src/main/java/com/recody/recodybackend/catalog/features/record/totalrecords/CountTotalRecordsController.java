package com.recody.recodybackend.catalog.features.record.totalrecords;

import com.recody.recodybackend.Monthly;
import com.recody.recodybackend.common.web.SuccessResponseBody;
import com.recody.recodybackend.commonbootutils.jwt.JwtManager;
import com.recody.recodybackend.commonbootutils.web.AccessToken;
import com.recody.recodybackend.record.RecordCount;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
class CountTotalRecordsController {
    
    private final MessageSource ms;
    private final JwtManager jwtManager;
    
    private final CountTotalRecordsHandler<RecordCount> countTotalRecordsHandler;
    
    
    @GetMapping( "/api/v1/record/records/total" )
    public ResponseEntity<SuccessResponseBody> countRecords(HttpServletRequest httpServletRequest,
                                                            @RequestParam( defaultValue = "false" ) Boolean thisMonth,
                                                            @RequestParam( required = false ) String yearMonth,
                                                            @AccessToken String accessToken) {
        return ResponseEntity.ok(
                SuccessResponseBody.builder()
                                   .message( ms.getMessage( "record.records.count.total.succeeded", null,
                                                            httpServletRequest.getLocale() ) )
                                   .data( countTotalRecordsHandler.handle(
                                           CountTotalRecords.builder()
                                                            .userId( jwtManager.resolveUserId( accessToken ) )
                                                            .monthly(
                                                                    thisMonth ? Monthly.thisMonth() : Monthly.of(
                                                                            yearMonth ) )
                                                            .build() ) )
                                   .build() );
    }
    
    @GetMapping( "/api/v1/record/records/total-data" )
    public ResponseEntity<RecordCount> countRecordsData(HttpServletRequest httpServletRequest,
                                                                      @RequestParam( defaultValue = "false" ) Boolean thisMonth,
                                                                      @RequestParam( required = false ) String yearMonth,
                                                                      @RequestParam Long userId) {
        return ResponseEntity.ok(
                countTotalRecordsHandler.handle(
                        CountTotalRecords.builder()
                                         .userId( userId )
                                         .monthly(
                                                 thisMonth ? Monthly.thisMonth() : Monthly.of( yearMonth ) )
                                         .build()
                                               )
                                );
        
    }
}
