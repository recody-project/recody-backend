package com.recody.recodybackend.movie.features.resolvecontentroot;

import org.springframework.stereotype.Component;

@Component
class DefaultContentRootResolver implements ContentRootResolver{
    
    // TODO 구현: Movie 서비스 만으로는 구현하기 힘드므로 임시로 처리하자.
    @Override
    public ResolveContentRootResult handle(ResolveContentRoot command) {
        return null;
    }
}
