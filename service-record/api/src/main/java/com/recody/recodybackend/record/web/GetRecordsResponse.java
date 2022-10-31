package com.recody.recodybackend.record.web;

import com.recody.recodybackend.record.Record;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GetRecordsResponse {
    
    private List<Record> records;
    private int size;
    
    @Override
    public String toString() {
        return "{\"GetRecordsResponse\":{" + "\"records\":" + records + ", \"size\":" + size + "}}";
    }
}
