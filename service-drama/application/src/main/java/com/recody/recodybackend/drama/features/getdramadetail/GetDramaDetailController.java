package com.recody.recodybackend.drama.features.getdramadetail;

import com.recody.recodybackend.drama.DramaDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
class GetDramaDetailController {
    
    private final GetDramaDetailHandler<DramaDetail> getDramaDetailHandler;
    
    @GetMapping( "/api/v1/drama/{dramaId}/detail" )
    public DramaDetail getDramaDetail(@PathVariable String dramaId,
                                      HttpServletRequest request) {
        return getDramaDetailHandler.handle(
                GetDramaDetail.builder()
                              .dramaId( dramaId )
                              .locale( request.getLocale() )
                              .build() );
    }
}
