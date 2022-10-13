package com.recody.recodybackend.catalog.features;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@AllArgsConstructor
@Data
public class LeaveRatingResponse {
    
    private UUID eventId;
    
}
