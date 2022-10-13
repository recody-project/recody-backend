package com.recody.recodybackend.common.events;

import lombok.*;

import java.util.UUID;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ContentRated {
    private UUID eventId;
    private String contentId;
    private Long userId;
    private Integer score;
    
    @Override
    public String toString() {
        return "{\"ContentRated\":{" + "\"eventId\":" + ((eventId != null) ? ("\"" + eventId + "\"") : null) + ", \"contentId\":" + ((contentId != null) ? ("\"" + contentId + "\"") : null) + ", \"userId\":" + userId + ", \"score\":" + score + "}}";
    }
}
