package com.recody.recodybackend.record.features;

import com.recody.recodybackend.record.Record;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetRecordResponse {
    private Record record;
    
    @Override
    public String toString() {
        return "{\"GetRecordResponse\":{" + "\"record\":" + record + "}}";
    }
}
