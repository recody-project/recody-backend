package com.recody.recodybackend.movie.data.title;

import com.recody.recodybackend.movie.data.movie.MovieEntity;
import com.recody.recodybackend.movie.exceptions.NoMovieTitleFoundException;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Locale;

@Mapper(componentModel = "spring",
        uses = {MovieTitleRepository.class
})
public abstract class MovieTitleMapper {
    @Autowired
    private MovieTitleRepository repository;
    
    public String map(MovieEntity entity, Locale locale) {
        if (locale.equals(Locale.KOREAN)){
            MovieTitleEntity movieTitleEntity = repository.findByMovie(entity)
                                                          .orElseThrow(NoMovieTitleFoundException::new);
            String koreanTitle = movieTitleEntity.getKoreanTitle();
            if (koreanTitle == null){
                return entity.getOriginalTitle();
            }
            return koreanTitle;
        }
        if (locale.equals(Locale.ENGLISH)){
            MovieTitleEntity movieTitleEntity = repository.findByMovie(entity)
                                                          .orElseThrow(NoMovieTitleFoundException::new);
            String englishTitle = movieTitleEntity.getEnglishTitle();
            if (englishTitle == null){
                return entity.getOriginalTitle();
            }
            return englishTitle;
        }
        return entity.getOriginalTitle();
    }
}
