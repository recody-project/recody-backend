package com.recody.recodybackend.movie.features.resolvecontentroot;

import com.recody.recodybackend.common.contents.Category;
import com.recody.recodybackend.common.contents.ContentSource;
import lombok.Builder;

import java.util.List;

@Builder
public class ResolveContentRoot {
    private Category category;
    private ContentSource contentSource;
    private List<Integer> contentId;
}
