package com.recody.recodybackend.record;

import lombok.Data;

import java.time.LocalDate;

@Data
public class RecordContent {
    
    private String contentId;
    private String imageUrl;
    private String title;
    private LocalDate appreciationDate;
    
}
