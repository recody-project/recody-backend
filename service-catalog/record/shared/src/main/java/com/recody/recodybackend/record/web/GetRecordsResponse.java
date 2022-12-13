package com.recody.recodybackend.record.web;

import com.recody.recodybackend.common.data.QueryMetadata;
import com.recody.recodybackend.record.Record;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GetRecordsResponse {
    
    private QueryMetadata metadata;
    private List<Record> records;
    
    @Override
    public String toString() {
        return "{\"GetRecordsResponse\":{"
               + "\"metadata\":" + metadata
               + ", \"records\":" + records
               + "}}";
    }
}
