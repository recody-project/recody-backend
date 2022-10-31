package com.recody.recodybackend.record.web;

import com.recody.recodybackend.record.RecordContent;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GetMyRecordContentsResponse {
    
    private List<RecordContent> contents;
    
}
