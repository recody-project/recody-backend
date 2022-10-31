package com.recody.recodybackend.record.web;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DeleteRecordResponse {
    
    private LocalDateTime deletedAt;
    
}
