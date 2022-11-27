package com.recody.recodybackend.record.web;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class AddRecordRequest {
    
    @NotNull(message = "{notNull}")
    private String contentId;
    private String title;
    private String note;
    
    private Integer appreciationNumber;
    
    @DateTimeFormat(style = "yyyy-MM-dd")
    private LocalDate appreciationDate;
}
