package com.recody.recodybackend.movie.features.resolvecontentroot;

import com.recody.recodybackend.movie.general.Category;
import com.recody.recodybackend.movie.general.ContentSource;
import lombok.Builder;

import java.util.List;

@Builder
public class ResolveContentRoot {
    private Category category;
    private ContentSource contentSource;
    private List<Integer> contentId;
}
