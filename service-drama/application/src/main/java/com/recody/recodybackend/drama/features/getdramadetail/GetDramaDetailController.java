package com.recody.recodybackend.drama.features.getdramadetail;

import com.recody.recodybackend.common.web.SuccessResponseBody;
import com.recody.recodybackend.drama.DramaDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
class GetDramaDetailController {
    
    private final GetDramaDetailHandler<DramaDetail> getDramaDetailHandler;
    
    private final MessageSource messageSource;
    @GetMapping( "/api/v1/drama/{dramaId}/detail" )
    public SuccessResponseBody getDramaDetail(@PathVariable String dramaId,
                                              HttpServletRequest request) {
        return SuccessResponseBody.builder()
                       .message( messageSource.getMessage( "drama.detail.succeeded",
                                                           null,
                                                           "드라마 상세정보를 반환합니다.",
                                                           request.getLocale() ) )
                       .data( getDramaDetailHandler.handle(
                               GetDramaDetail.builder()
                                             .dramaId( dramaId )
                                             .locale( request.getLocale() )
                                             .build() ) )
                                  .build();
    }
    
    @GetMapping( "/api/v1/drama/{dramaId}/detail-data" )
    public DramaDetail getDramaDetailData(@PathVariable String dramaId,
                                      HttpServletRequest request) {
        return getDramaDetailHandler.handle(
                GetDramaDetail.builder()
                              .dramaId( dramaId )
                              .locale( request.getLocale() )
                              .build() );
    }
}
