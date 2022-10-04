package com.recody.recodybackend.catalog.web;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class DeleteFromWishlistRequest {
    @NotNull
    private String contentId;
    @NotNull
    private String category;
}
