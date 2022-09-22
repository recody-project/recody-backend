package com.recody.recodybackend.record.features;

import com.recody.recodybackend.record.Record;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GetRecordsResponse {
    
    private List<Record> records;
    private int size;
    
}
