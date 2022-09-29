package com.recody.recodybackend.catalog.features.search;

import com.recody.recodybackend.catalog.PersonalizedContent;
import com.recody.recodybackend.catalog.PersonalizedMovie;
import com.recody.recodybackend.catalog.features.personalize.ContentPersonalizer;
import com.recody.recodybackend.catalog.features.search.movies.SearchMovies;
import com.recody.recodybackend.catalog.features.search.movies.SearchMoviesHandler;
import com.recody.recodybackend.common.contents.Category;
import com.recody.recodybackend.common.exceptions.UnsupportedCategoryException;
import com.recody.recodybackend.movie.Movie;
import com.recody.recodybackend.movie.features.searchmovies.SearchMoviesResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
class DefaultSearchContentHandler implements SearchContentHandler{
    
    private final SearchMoviesHandler searchMoviesHandler;
    private final ContentPersonalizer<Movie, PersonalizedMovie> moviePersonalizer;
    
    @Override
    public SearchContentResponse handle(SearchContent command) {
        String keyword = command.getKeyword();
        Category category = command.getCategory();
        Long userId = command.getUserId();
        List<PersonalizedContent> personalizedContents = new ArrayList<>();
        
        if (category.equals(Category.Movie)){
            SearchMoviesResult movieResult = searchMoviesHandler.handle(
                    SearchMovies.builder().keyword(keyword).language(command.getLanguage()).build());
            List<Movie> movies = movieResult.getMovies();
            // 각 영화 정보에 설정된 장르를 수정한다.
            for (Movie movie : movies) {
                personalizedContents.add(moviePersonalizer.personalize(movie, userId));
            }
        } else {
            // 현재는 영화만 지원한다.
            throw new UnsupportedCategoryException();
        }
        
        return new SearchContentResponse(personalizedContents);
    }
}
