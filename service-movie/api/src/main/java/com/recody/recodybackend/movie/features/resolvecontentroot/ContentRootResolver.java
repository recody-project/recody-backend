package com.recody.recodybackend.movie.features.resolvecontentroot;

public interface ContentRootResolver {
    ResolveContentRootResult handle(ResolveContentRoot command);
}
