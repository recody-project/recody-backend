package com.recody.recodybackend.catalog.web;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ModifyCategoryRequest {
    
    @NotNull
    private String name;
    @NotNull
    private String iconUrl;
    
}
