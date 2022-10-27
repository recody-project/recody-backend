package com.recody.recodybackend.catalog.web;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AddCategoryRequest {
    @NotNull(message = "${notNull}")
    private String name;
    private String iconUrl;
    
}
