package com.recody.recodybackend.catalog.features.searchmovies;

import com.recody.recodybackend.common.openapi.SimpleAPIRequest;
import com.recody.recodybackend.common.openapi.SimpleAPIRequester;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
class SearchMovies {
    
    @Value(value = "${catalog.movie.search.url}")
    private String movieUrl;
    
    public String searchMoviesString(String movieName){
        SimpleAPIRequest simpleAPIRequest = new SimpleAPIRequest(movieUrl);
        simpleAPIRequest.addRequestParam("movieName", movieName);
        SimpleAPIRequester requester = new SimpleAPIRequester();
        System.out.println(simpleAPIRequest);
    
        String string = requester.executeToString(simpleAPIRequest);
        System.out.println(string);
        return string;
    }
}
