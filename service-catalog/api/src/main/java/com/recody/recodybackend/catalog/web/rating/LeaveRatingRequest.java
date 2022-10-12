package com.recody.recodybackend.catalog.web.rating;

import lombok.Data;

@Data
public class LeaveRatingRequest {
    private String contentId;
    private Integer score;
}
