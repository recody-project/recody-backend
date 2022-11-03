package com.recody.recodybackend.catalog.web;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@AllArgsConstructor
@Data
public class AddRatingResponse {
    
    private UUID eventId;
    
}
