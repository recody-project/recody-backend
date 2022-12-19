package com.recody.recodybackend.drama.features.searchdrama;

import com.recody.recodybackend.drama.Dramas;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
public class SearchDramasController {
    
    private final SearchDramaHandler<Dramas> searchDramaHandler;
    
    @GetMapping( "/api/v1/drama/search" )
    public Dramas searchDramas(@RequestParam String keyword,
                               HttpServletRequest request) {
        return searchDramaHandler.handle(
                SearchDramas.builder()
                            .keyword( keyword )
                            .language( request.getLocale() )
                            .build() );
    }
}
