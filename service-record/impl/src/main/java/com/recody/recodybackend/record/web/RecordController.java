package com.recody.recodybackend.record.web;

import com.recody.recodybackend.common.web.SuccessResponseBody;
import com.recody.recodybackend.commonbootutils.jwt.JwtManager;
import com.recody.recodybackend.commonbootutils.web.AccessToken;
import com.recody.recodybackend.record.features.RecordService;
import com.recody.recodybackend.record.features.addrecord.AddRecord;
import com.recody.recodybackend.record.features.completerecord.CompleteRecord;
import com.recody.recodybackend.record.features.getmyrecords.GetMyRecords;
import com.recody.recodybackend.record.features.getrecord.GetRecord;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class RecordController {
    
    private final MessageSource ms;
    private final RecordService recordService;
    private final JwtManager jwtManager;
    
    @PostMapping("/api/v1/record")
    public ResponseEntity<SuccessResponseBody> postRecord(HttpServletRequest httpServletRequest,
                                                          @Valid @RequestBody AddRecordRequest request,
                                                          @AccessToken String accessToken) {
        
        return ResponseEntity.ok(SuccessResponseBody
                                         .builder()
                                         .message(ms.getMessage("record.add.succeeded", null,
                                                                httpServletRequest.getLocale()))
                                         .data(recordService.addRecord(createAddRecordCommand(request, accessToken)))
                                         .build());
    }
    
    @GetMapping("/api/v1/record/{recordId}")
    public ResponseEntity<SuccessResponseBody> getRecord(HttpServletRequest httpServletRequest,
                                                         @PathVariable String recordId) {
        return ResponseEntity.ok(SuccessResponseBody
                                         .builder()
                                         .message(ms.getMessage("record.get.succeeded", null,
                                                                httpServletRequest.getLocale()))
                                         .data(recordService.getRecord(GetRecord.builder().recordId(recordId).build()))
                                         .build());
    }
    
    @GetMapping("/api/v1/record/records")
    public ResponseEntity<SuccessResponseBody> getRecords(HttpServletRequest httpServletRequest,
                                                          @Nullable @RequestParam(defaultValue = "0") Integer page,
                                                          @Nullable @RequestParam(defaultValue = "10") Integer size,
                                                          @AccessToken String accessToken) {
        return ResponseEntity.ok(SuccessResponseBody
                                         .builder()
                                         .message(ms.getMessage("record.records.get.succeeded", null,
                                                                httpServletRequest.getLocale()))
                                         .data(recordService.getRecords(GetMyRecords.builder()
                                                                                    .userId(jwtManager.resolveUserId(accessToken))
                                                                                    .page(page)
                                                                                    .size(size)
                                                                                    .build()))
                                         .build());
    
    }
    
    private AddRecord createAddRecordCommand(AddRecordRequest request, String accessToken) {
        return AddRecord
                .builder()
                .contentId(request.getContentId())
                .note(request.getNote())
                .userId(jwtManager.resolveUserId(accessToken))
                .build();
    }
    
    @PostMapping("/api/v1/record/complete")
    public ResponseEntity<SuccessResponseBody> complete(HttpServletRequest httpServletRequest,
                                                        @Valid @RequestBody CompleteRecordRequest request,
                                                        @AccessToken String accessToken) {
        return ResponseEntity.ok(SuccessResponseBody
                                         .builder()
                                         .message(ms.getMessage("record.complete.succeeded", null,
                                                                httpServletRequest.getLocale()))
                                         .data(recordService.completeRecord(CompleteRecord
                                                                                    .builder()
                                                                                    .recordId(request.getRecordId())
                                                                                    .note(request.getNote())
                                                                                    .build()))
                                         .build());
    }
    
}
