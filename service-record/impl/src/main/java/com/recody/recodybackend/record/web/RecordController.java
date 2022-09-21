package com.recody.recodybackend.record.web;

import com.recody.recodybackend.common.web.SuccessResponseBody;
import com.recody.recodybackend.record.features.RecordService;
import com.recody.recodybackend.record.features.addrecord.AddRecord;
import com.recody.recodybackend.record.features.getrecord.GetRecord;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@RestController
@RequiredArgsConstructor
public class RecordController {
    
    private final MessageSource ms;
    private final RecordService recordService;
    
    @PostMapping("/api/v1/record")
    public ResponseEntity<SuccessResponseBody> postRecord(HttpServletRequest httpServletRequest,
                                                          @Valid @RequestBody AddRecordRequest request) {
        
        return ResponseEntity.ok(SuccessResponseBody
                                         .builder()
                                         .message(ms.getMessage("record.add.succeeded", null,
                                                                httpServletRequest.getLocale()))
                                         .data(recordService.addRecord(createAddRecordCommand(request)))
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
    
    private AddRecord createAddRecordCommand(AddRecordRequest request) {
        return AddRecord
                .builder()
                .contentId(request.getContentId())
                .note(request.getNote())
                .userId(request.getUserId())
                .build();
    }
    
}
