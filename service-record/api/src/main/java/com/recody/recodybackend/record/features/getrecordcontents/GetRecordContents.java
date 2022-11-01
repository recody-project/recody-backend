package com.recody.recodybackend.record.features.getrecordcontents;


import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GetRecordContents {
    
    private Long userId;
    private String categoryId;
    private Integer size;
    private Integer page;

}
