package com.recody.recodybackend.record.features.getrecordcontents;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetRecordContents {
    
    private Long userId;
    private Integer size;
    private Integer page;

}
