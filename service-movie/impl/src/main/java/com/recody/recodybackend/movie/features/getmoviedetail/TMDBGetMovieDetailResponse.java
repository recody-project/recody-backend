package com.recody.recodybackend.movie.features.getmoviedetail;

import com.fasterxml.jackson.databind.JsonNode;
import com.recody.recodybackend.common.openapi.JsonAPIResponse;
import com.recody.recodybackend.movie.general.MovieGenre;
import com.recody.recodybackend.movie.general.MovieSource;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class TMDBGetMovieDetailResponse implements GetMovieDetailResponse {
    
    private static final String GENRES = "genres";
    private static final String IMDB_ID = "imdb_id";
    private static final String RESULTS = "results";
    private static final String GENRE_IDS = "genre_ids";
    private static final String ID = "id";
    private static final String ORIGINAL_LANGUAGE = "original_language";
    private static final String ORIGINAL_TITLE = "original_title";
    private static final String OVERVIEW = "overview";
    private static final String RELEASE_DATE = "release_date";
    private static final String TITLE = "title";
    private static final String POSTER_PATH = "poster_path";
    private static final String POPULARITY = "popularity";
    private static final String PRODUCTION_COUNTRIES = "production_countries";
    private static final String VOTE_COUNT = "vote_count";
    private static final String VOTE_AVERAGE = "vote_average";
    private static final String STATUS = "status";
    private static final String ISO_639_1 = "iso_639_1";
    private static final String SPOKEN_LANGUAGES = "spoken_languages";
    private static final String REVENUE = "revenue";
    private static final String RUNTIME = "runtime";
    private static final String NAME = "name";
    private static final String ISO_3166_1 = "iso_3166_1";
    
    
    private final JsonAPIResponse response;
    
    public TMDBGetMovieDetailResponse(JsonAPIResponse response) { this.response = response; }
    
    @Override
    public MovieSource getContentSource() { return MovieSource.TMDB; }
    
    @Override
    public List<MovieGenre> getGenres() {
        return response.rawStream().map(node -> {
            JsonNode jsonNode = node.get(GENRES); // go, itIsArray
            ArrayList<MovieGenre> genres = new ArrayList<>(); // iterate
            for (JsonNode genreNode : jsonNode) {
                genres.add(new MovieGenre(genreNode.get("id").asInt(), genreNode.get("name").asText()));
            }
            log.debug("genres: {}", genres);
            return genres;
        }).findAny().orElseThrow(() -> new RuntimeException("스트림 처리 실패 "));
    }
    
    @Override
    public Integer getId() {
        return response.visitorStream().go(ID).whichIsInteger().get();
    }
    
    @Override
    public String getImdbId() {
        return response.visitorStream().go(IMDB_ID).whichIsText().get();
    }
    
    @Override
    public String getOriginalLanguage() {
        return response.visitorStream().go(ORIGINAL_LANGUAGE).whichIsText().get();
    }
    
    @Override
    public String getOriginalTitle() {
        return response.visitorStream().go(ORIGINAL_TITLE).whichIsText().get();
    }
    
    @Override
    public String getOverview() {
        return response.visitorStream().go(OVERVIEW).whichIsText().get();
    }
    
    @Override
    public Float getPopularity() {
        return response.getResponse().get(POPULARITY).floatValue();
    }
    
    @Override
    public String getPosterPath() {
        return response.getResponse().get(POSTER_PATH).asText();
    }
    
    @Override
    public List<ProductionCountry> getProductionCountries() {
        return response
                .visitorStream()
                .go(PRODUCTION_COUNTRIES)
                .whichIsArray()
                .andIterate()
                .whereNamesAre(ISO_3166_1, NAME) // which is object?
                .whichTypesAre(String.class, String.class)
                .collectInto(ProductionCountry.class);
    }
    
    @Override
    public String getReleaseDate() {
        return response.getResponse().get(RELEASE_DATE).asText();
    }
    
    @Override
    public Integer getRuntime() {
        return response.getResponse().get(RUNTIME).asInt();
    }
    
    @Override
    public Integer getRevenue() {
        return response.getResponse().get(REVENUE).asInt();
    }
    
    @Override
    public List<SpokenLanguage> getSpokenLanguages() {
        return response.visitorStream()
                       .go(SPOKEN_LANGUAGES).whichIsArray()
                       .andIterate()
                       .whereNamesAre(ISO_639_1, NAME)
                       .whichTypesAre(String.class, String.class)
                       .collectInto(SpokenLanguage.class);
    }
    
    @Override
    public String getStatus() {
        return response.getResponse().get(STATUS).asText();
    }
    
    @Override
    public String getTitle() {
        return response.getResponse().get(TITLE).asText();
    }
    
    @Override
    public Float getVoteAverage() {
        return response.getResponse().get(VOTE_AVERAGE).floatValue();
    }
    
    @Override
    public Integer getVoteCount() {
        return response.getResponse().get(VOTE_COUNT).asInt();
    }
}
