package com.recody.recodybackend.catalog.web;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChangeGenresOnContentRequest {

    private List<String> genreIds;

}
