package com.recody.recodybackend.movie.data.movie;

import com.recody.recodybackend.common.contents.BasicCategory;
import com.recody.recodybackend.movie.MovieDetail;
import com.recody.recodybackend.movie.data.genre.MovieGenreMapper;
import com.recody.recodybackend.movie.data.people.MoviePersonMapper;
import com.recody.recodybackend.movie.data.productioncountry.ProductionCountryMapper;
import com.recody.recodybackend.movie.data.spokenlanguage.SpokenLanguageMapper;
import com.recody.recodybackend.movie.data.title.MovieTitleMapper;
import com.recody.recodybackend.movie.features.getmoviedetail.dto.TMDBMovieDetail;
import com.recody.recodybackend.movie.features.getmoviedetail.dto.TMDBMovieGenre;
import com.recody.recodybackend.movie.features.tmdb.TMDB;
import com.recody.recodybackend.movie.general.MovieGenre;
import com.recody.recodybackend.movie.general.MovieSource;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Locale;

@Mapper(componentModel = "spring",
        imports = {MovieSource.class, BasicCategory.class, TMDB.class},
        uses = {
        MovieGenreMapper.class,
        ProductionCountryMapper.class,
        SpokenLanguageMapper.class,
        MovieTitleMapper.class,
        MoviePersonMapper.class
})
@RequiredArgsConstructor
public abstract class MovieDetailMapper {
    
    
    @Mapping(target = "directors", ignore = true)
    @Mapping(target = "actors", ignore = true)
    @Mapping(target = "category", expression = "java(BasicCategory.Movie)")
    @Mapping(target = "contentId", ignore = true)
    @Mapping(target = "rootId", ignore = true)
    @Mapping(target = "source", expression = "java(MovieSource.TMDB)")
    @Mapping(target = "tmdbId", source = "tmdbMovieDetail.id")
    @Mapping(target = "posterPath", expression = "java(TMDB.fullPosterPath(tmdbMovieDetail.getPosterPath()))")
    public abstract MovieDetail map(TMDBMovieDetail tmdbMovieDetail);
    
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "tmdbId",
             source = "movieDetail.tmdbId",
             conditionExpression = "java(movieDetail.getSource() == MovieSource.TMDB)")
    @Mapping(target = "productionCountries", source = "productionCountries")
    @Mapping(target = "title", ignore = true)
    @Mapping(target = "actors", ignore = true)
    @Mapping(target = "directors", ignore = true)
    public abstract MovieEntity toEntity(MovieDetail movieDetail);
    
    
    @Mapping(target = "category", expression = "java(com.recody.recodybackend.common.contents.BasicCategory.Movie)")
    @Mapping(target = "contentId", source = "movie.id")
    @Mapping(target = "source", expression = "java((s.equals(MovieSource.TMDB)) ? MovieSource.TMDB : null)")
    @Mapping(target = "rootId", ignore = true)
    @Mapping(target = "tmdbId", source = "movie.tmdbId", conditionExpression = "java(MovieSource.TMDB.equals(s))")
    @Mapping(target = "title", expression = "java(this.mapTitle(movie, locale))")
    public abstract MovieDetail map(MovieEntity movie, MovieSource s, Locale locale);
    
    public MovieGenre toGenre(TMDBMovieGenre tmdbMovieGenre){
        MovieGenre movieGenre = new MovieGenre();
        movieGenre.setGenreId(tmdbMovieGenre.getId());
        movieGenre.setGenreName(tmdbMovieGenre.getName());
        movieGenre.setSource(MovieSource.TMDB);
        return movieGenre;
    }
    
    public String mapTitle(MovieEntity entity, Locale locale) {
        if (locale.equals(Locale.KOREAN)){
            String koreanTitle = entity.getTitle().getKoreanTitle();
            if (koreanTitle == null){
                return entity.getOriginalTitle();
            }
            return koreanTitle;
        }
        if (locale.equals(Locale.ENGLISH)){
            String englishTitle = entity.getTitle().getEnglishTitle();
            if (englishTitle == null){
                return entity.getOriginalTitle();
            }
            return englishTitle;
        }
        return entity.getOriginalTitle();
    }
}
