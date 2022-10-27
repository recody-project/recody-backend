package com.recody.recodybackend.catalog.web.rating;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class LeaveRatingRequest {
    @NotNull(message = "${notNull}")
    private String contentId;
    private Integer score;
}
