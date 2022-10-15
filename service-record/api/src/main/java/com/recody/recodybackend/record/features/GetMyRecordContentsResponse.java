package com.recody.recodybackend.record.features;

import com.recody.recodybackend.record.RecordContent;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GetMyRecordContentsResponse {
    
    private List<RecordContent> contents;
    
}
