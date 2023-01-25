package com.recody.recodybackend.drama.features.searchdrama;

import com.recody.recodybackend.common.contents.GenreIds;
import com.recody.recodybackend.common.web.SuccessResponseBody;
import com.recody.recodybackend.drama.Dramas;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class SearchDramasController {
    
    private final SearchDramaHandler<Dramas> searchDramaHandler;
    
    private final MessageSource ms;
    
    @GetMapping( "/api/v1/drama/search" )
    public SuccessResponseBody searchDramas(@RequestParam String keyword,
                                            HttpServletRequest request,
                                            @RequestParam( required = false ) List<String> genreId,
                                            @RequestParam( required = false, defaultValue = "1" ) Integer page) {
        return SuccessResponseBody.builder()
                                  .message( ms.getMessage( "drama.search.succeeded", null, null,
                                                           request.getLocale() ) )
                                  .data( searchDramaHandler.handle(
                                          SearchDramas.builder()
                                                      .keyword( keyword )
                                                      .locale( request.getLocale() )
                                                      .genreIds( GenreIds.of( genreId ) )
                                                      .page( page )
                                                      .build() ) )
                                  .build();
    }
    
    @GetMapping( "/api/v1/drama/search-data" )
    public Dramas searchDramasData(@RequestParam String keyword,
                                   HttpServletRequest request) {
        return searchDramaHandler.handle(
                SearchDramas.builder()
                            .keyword( keyword )
                            .locale( request.getLocale() )
                            .build() );
    }
}
