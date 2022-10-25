package com.recody.recodybackend.movie.data.movie;

import com.recody.recodybackend.common.contents.BasicCategory;
import com.recody.recodybackend.common.contents.movie.Actor;
import com.recody.recodybackend.common.contents.movie.Director;
import com.recody.recodybackend.common.contents.movie.MovieDetail;
import com.recody.recodybackend.common.contents.movie.MovieSource;
import com.recody.recodybackend.movie.data.genre.MovieGenreMapper;
import com.recody.recodybackend.movie.data.people.MoviePersonMapper;
import com.recody.recodybackend.movie.data.productioncountry.ProductionCountryMapper;
import com.recody.recodybackend.movie.data.spokenlanguage.SpokenLanguageMapper;
import com.recody.recodybackend.movie.data.title.MovieTitleMapper;
import com.recody.recodybackend.movie.features.getmoviedetail.MovieInfo;
import com.recody.recodybackend.movie.features.getmoviedetail.dto.TMDBMovieDetail;
import com.recody.recodybackend.movie.features.getmoviedetail.fromapi.TMDBFetchedMovieDetail;
import com.recody.recodybackend.movie.features.tmdb.TMDB;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.*;

import java.util.List;
import java.util.Locale;

@Mapper(componentModel = "spring",
        imports = {MovieSource.class, BasicCategory.class, TMDB.class},
        builder = @Builder(disableBuilder = true),
        uses = {MovieGenreMapper.class, ProductionCountryMapper.class, SpokenLanguageMapper.class, MovieTitleMapper.class,
           MoviePersonMapper.class})
@RequiredArgsConstructor
@Slf4j
public abstract class MovieDetailMapper {
    
    @Named("fullPosterPath")
    public String makeFullPosterPath(String newPosterPath){
        return TMDB.fullPosterPath(newPosterPath);
    }
    
    /**
     * TMDB 에서 가져온 영화 정보를
     */
    @Mapping(target = "directors", source = "directors")
    @Mapping(target = "actors", source = "actors")
    @Mapping(target = "category", expression = "java(BasicCategory.Movie)")
    @Mapping(target = "contentId", source = "movieInfo.contentId")
//    @Mapping(target = "rootId", ignore = true)
    @Mapping(target = "source", expression = "java(com.recody.recodybackend.common.contents.movie.MovieSource.TMDB)")
//    @Mapping(target = "tmdbId", source = "movieInfo.tmdbId")
    @Mapping(target = "genres", source = "movieInfo.genres")
    public abstract MovieDetail makeMovieDetail(MovieInfo movieInfo, List<Actor> actors, List<Director> directors);
    
    
    @Mapping(target = "tmdbId", ignore = true)
    @Mapping(target = "directors", ignore = true)
    @Mapping(target = "actors", ignore = true)
    @Mapping(target = "title", source = ".")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "lastModifiedAt", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "posterPath",
             source = "detail.posterPath",
             qualifiedByName = "fullPosterPath",
             conditionExpression = "java((detail.getPosterPath() != null))")
    @Mapping(target = "genres", ignore = true) // 업데이트할 때에는 dto 에서 MovieGenreEntity 를 만들 수 없다.
    public abstract MovieEntity update(@MappingTarget MovieEntity entity, TMDBMovieDetail detail,
                                       @Context Locale locale);
    
    @Mapping(target = "category", expression = "java(com.recody.recodybackend.common.contents.BasicCategory.Movie)")
    @Mapping(target = "contentId", source = "entity.id")
    @Mapping(target = "source", expression = "java((s.equals(MovieSource.TMDB)) ? MovieSource.TMDB : null)")
//    @Mapping(target = "tmdbId", source = "entity.tmdbId", conditionExpression = "java(MovieSource.TMDB.equals(s))")
    @Mapping(target = "title", source = ".")
    @Mapping(target = "genres", source = "entity.genres")
    @Mapping(target = "actors", source = "entity.actors")
    public abstract MovieDetail map(MovieEntity entity, @Context MovieSource s, @Context Locale locale);
    
    @Mapping(target = "directors", ignore = true)
    @Mapping(target = "contentId", source = "args.id")
    @Mapping(target = "category", expression = "java(BasicCategory.Movie)")
    @Mapping(target = "actors", ignore = true)
    @Mapping(target = "genres", source = "args.genres")
    public abstract TMDBFetchedMovieDetail toFetchedMovieDetail(TMDBMovieDetail args);
    
    
    @Mapping(target = "tmdbId", source = "args.id")
    @Mapping(target = "directors", ignore = true)
    @Mapping(target = "actors", ignore = true)
    @Mapping(target = "productionCountries", ignore = true)
    @Mapping(target = "spokenLanguages", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "posterPath", expression = "java(TMDB.fullPosterPath(args.getPosterPath()))")
    @Mapping(target = "title", source = "args.title")
    public abstract MovieEntity newEntity(TMDBMovieDetail args, @Context Locale locale);
}
