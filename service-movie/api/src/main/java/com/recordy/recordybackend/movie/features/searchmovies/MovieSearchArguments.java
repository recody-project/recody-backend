package com.recordy.recordybackend.movie.features.searchmovies;

import lombok.Getter;

public class MovieSearchArguments {
    
    @Getter
    private String movieName;
    @Getter
    private String language;
    
    public void movieName(String movieName){
        this.movieName = movieName;
    }
    
    public void language(String language){
        this.language = language;
    }
    
    @Override
    public String toString() {
        return "{\"MovieSearchArguments\":{" + "" + ((movieName != null) ? ("\"movieName\":\"" + movieName + "\"") : ("\"movieName\":" + null)) + "," + ((language != null) ? ("\"language\":\"" + language + "\"") : ("\"language\":" + null)) + "}}";
    }
}
