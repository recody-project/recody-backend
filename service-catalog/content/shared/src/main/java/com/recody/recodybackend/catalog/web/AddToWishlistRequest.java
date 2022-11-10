package com.recody.recodybackend.catalog.web;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AddToWishlistRequest {
    @NotNull(message = "${notNull}")
    private String contentId;
    
}
