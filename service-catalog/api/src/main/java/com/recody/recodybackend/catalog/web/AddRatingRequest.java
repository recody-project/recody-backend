package com.recody.recodybackend.catalog.web;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AddRatingRequest {
    @NotNull(message = "${notNull}")
    private String contentId;
    @NotNull(message = "${notNull}")
    private Integer score;
    
}
