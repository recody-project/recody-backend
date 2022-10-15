package com.recody.recodybackend.movie.data.movie;

import com.recody.recodybackend.common.contents.BasicCategory;
import com.recody.recodybackend.movie.Movie;
import com.recody.recodybackend.movie.data.genre.MovieGenreMapper;
import com.recody.recodybackend.movie.data.productioncountry.ProductionCountryMapper;
import com.recody.recodybackend.movie.data.spokenlanguage.SpokenLanguageMapper;
import com.recody.recodybackend.movie.data.title.MovieTitleEntity;
import com.recody.recodybackend.movie.data.title.MovieTitleMapper;
import com.recody.recodybackend.movie.data.title.MovieTitleRepository;
import com.recody.recodybackend.movie.exceptions.NoMovieTitleFoundException;
import com.recody.recodybackend.movie.features.getmoviedetail.dto.TMDBMovieDetail;
import com.recody.recodybackend.movie.features.getmoviedetail.dto.TMDBMovieGenre;
import com.recody.recodybackend.movie.general.MovieGenre;
import com.recody.recodybackend.movie.general.MovieSource;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Locale;

@Mapper(componentModel = "spring",
        imports = {MovieSource.class, BasicCategory.class},
        uses = {
        MovieGenreMapper.class,
        ProductionCountryMapper.class,
        SpokenLanguageMapper.class,
        MovieTitleMapper.class
})
@RequiredArgsConstructor
public abstract class MovieEntityMapper {
    

    @Autowired
    private MovieTitleRepository repository;
    
    
    @Mapping(target = "category", expression = "java(BasicCategory.Movie)")
    @Mapping(target = "movieId", ignore = true)
    @Mapping(target = "rootId", ignore = true)
    @Mapping(target = "source", expression = "java(MovieSource.TMDB)")
    @Mapping(target = "tmdbId", source = "tmdbMovieDetail.id")
    public abstract Movie map(TMDBMovieDetail tmdbMovieDetail);
    
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "tmdbId",
             source = "movie.tmdbId",
             conditionExpression = "java(movie.getSource() == MovieSource.TMDB)")
    @Mapping(target = "productionCountries", source = "productionCountries")
    @Mapping(target = "title", ignore = true)
    public abstract MovieEntity toEntity(Movie movie);
    
    
    @Mapping(target = "category", expression = "java(com.recody.recodybackend.common.contents.BasicCategory.Movie)")
    @Mapping(target = "movieId", source = "movie.id")
    @Mapping(target = "source", expression = "java((s.equals(MovieSource.TMDB)) ? MovieSource.TMDB : null)")
    @Mapping(target = "rootId", ignore = true)
    @Mapping(target = "tmdbId", source = "movie.tmdbId", conditionExpression = "java(MovieSource.TMDB.equals(s))")
    @Mapping(target = "title", expression = "java(this.map(movie, locale))")
    public abstract Movie map(MovieEntity movie, MovieSource s, Locale locale);
    
    
    public MovieGenre toGenre(TMDBMovieGenre tmdbMovieGenre){
        MovieGenre movieGenre = new MovieGenre();
        movieGenre.setGenreId(tmdbMovieGenre.getId());
        movieGenre.setGenreName(tmdbMovieGenre.getName());
        movieGenre.setSource(MovieSource.TMDB);
        return movieGenre;
    }
    
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
