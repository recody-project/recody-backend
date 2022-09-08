package com.recody.recodybackend.movie.features.getmovieinfo;

import com.fasterxml.jackson.databind.JsonNode;
import com.recody.recodybackend.movie.general.MovieSource;

public class TMDBMovieInfoResponse implements MovieInfoResponse {
    
    private final JsonNode response;
    
    public TMDBMovieInfoResponse(JsonNode response) { this.response = response; }
    
    public JsonNode getResponse() { return response; }
    
    @Override
    public MovieSource getContentSource() { return MovieSource.TMDB; }
}
