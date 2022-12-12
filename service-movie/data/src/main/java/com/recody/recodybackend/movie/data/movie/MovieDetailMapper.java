package com.recody.recodybackend.movie.data.movie;

import com.recody.recodybackend.common.contents.BasicCategory;
import com.recody.recodybackend.movie.*;
import com.recody.recodybackend.movie.data.genre.MovieGenreMapper;
import com.recody.recodybackend.movie.data.overview.MovieOverviewMapper;
import com.recody.recodybackend.movie.data.people.MoviePersonMapper;
import com.recody.recodybackend.movie.data.title.MovieTitleMapper;
import com.recody.recodybackend.movie.features.getmoviedetail.dto.TMDBMovieDetail;
import com.recody.recodybackend.movie.features.getmoviedetail.fromapi.FetchedMovieDetailViewModel;
import com.recody.recodybackend.movie.features.getmoviedetail.fromapi.TMDBFetchedMovieDetail;
import com.recody.recodybackend.movie.features.tmdb.TMDB;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Locale;

@Mapper( componentModel = "spring",
         imports = {MovieSource.class, BasicCategory.class, TMDB.class, MovieTitleMapper.class},
         builder = @Builder( disableBuilder = true ),
         uses = {MovieGenreMapper.class,
                 MovieTitleMapper.class,
                 MoviePersonMapper.class,
                 MovieOverviewMapper.class} )
@RequiredArgsConstructor
@Slf4j
public abstract class MovieDetailMapper {
    
    @Autowired
    private MovieTitleMapper movieTitleMapper;
    
    @Autowired
    private MovieOverviewMapper movieOverviewMapper;
    
    @Named( "fullPosterPath" )
    public String makeFullPosterPath(String newPosterPath) {
        return TMDB.fullPosterPath( newPosterPath );
    }
    
    /**
     * TMDB 에서 가져온 영화 정보를
     */
    @Mapping( target = "directors", source = "directors" )
    @Mapping( target = "actors", source = "actors" )
    @Mapping( target = "category", expression = "java(BasicCategory.Movie)" )
    @Mapping( target = "contentId", source = "movieInfo.contentId" )
    @Mapping( target = "source", expression = "java(com.recody.recodybackend.movie.MovieSource.TMDB)" )
    @Mapping( target = "genres", source = "movieInfo.genres" )
    public abstract MovieDetail makeMovieDetail(MovieInfo movieInfo, List<Actor> actors,
                                                List<Director> directors);
    
    
    @Mapping( target = "tmdbId", ignore = true )
    @Mapping( target = "directors", ignore = true )
    @Mapping( target = "actors", ignore = true )
    @Mapping( target = "title", expression = "java(movieTitleMapper.update( entity, detail, locale ))" )
    @Mapping( target = "id", ignore = true )
    @Mapping( target = "lastModifiedAt", ignore = true )
    @Mapping( target = "createdAt", ignore = true )
    @Mapping( target = "posterPath",
              source = "detail.posterPath",
              qualifiedByName = "fullPosterPath",
              conditionExpression = "java((detail.getPosterPath() != null))" )
    @Mapping( target = "genres", ignore = true ) // 업데이트할 때에는 dto 에서 MovieGenreEntity 를 만들 수 없다.
    @Mapping( target = "overview",expression = "java(movieOverviewMapper.update( entity, detail, locale ))")
    public abstract MovieEntity update(@MappingTarget MovieEntity entity, TMDBMovieDetail detail,
                                       @Context Locale locale);
    
    @Mapping( target = "category",
              expression = "java(com.recody.recodybackend.common.contents.BasicCategory.Movie)" )
    @Mapping( target = "contentId", source = "entity.id" )
    @Mapping( target = "source", expression = "java((s.equals(MovieSource.TMDB)) ? MovieSource.TMDB : null)" )
    @Mapping( target = "title", source = "." )
    @Mapping( target = "genres", source = "entity.genres" )
    public abstract MovieDetail map(MovieEntity entity, @Context MovieSource s, @Context Locale locale);
    
    @Mapping( target = "directors", ignore = true )
    @Mapping( target = "contentId", source = "detail.id" )
    @Mapping( target = "category", expression = "java(BasicCategory.Movie)" )
    @Mapping( target = "actors", ignore = true )
    @Mapping( target = "genres", source = "detail.genres" )
    @Mapping( target = "posterPath",
              source = "detail.posterPath",
              qualifiedByName = "fullPosterPath",
              conditionExpression = "java((detail.getPosterPath() != null))" )
    public abstract TMDBFetchedMovieDetail toFetchedMovieDetail(TMDBMovieDetail detail);
    
    
    @Mapping( target = "actors", source = "actors", qualifiedByName = "concatActors" )
    @Mapping( target = "directors", source = "directors", qualifiedByName = "concatDirectors" )
    public abstract MovieDetailViewModel toViewModel(MovieDetail movieDetail);
    
    @Mapping( target = "actors", source = "actors", qualifiedByName = "concatActors" )
    @Mapping( target = "directors", source = "directors", qualifiedByName = "concatDirectors" )
    public abstract FetchedMovieDetailViewModel toViewModel(TMDBFetchedMovieDetail movieDetail);
    
    @Mapping( target = "tmdbId", source = "args.id" )
    @Mapping( target = "directors", ignore = true )
    @Mapping( target = "actors", ignore = true )
    @Mapping( target = "id", ignore = true )
    @Mapping( target = "posterPath", expression = "java(TMDB.fullPosterPath(args.getPosterPath()))" )
    @Mapping( target = "title", source = "args.title" )
    public abstract MovieEntity newEntity(TMDBMovieDetail args, @Context Locale locale);
    
    @Named( "concatActors" )
    public String concatActor(List<Actor> actors) {
        StringBuilder stringBuilder = new StringBuilder();
        if ( !actors.isEmpty() ) {
            for (Actor actor : actors) {
                if ( stringBuilder.length() != 0 ) {
                    stringBuilder.append( ", " );
                }
                stringBuilder.append( actor.getName() );
            }
        }
        return stringBuilder.toString();
    }
    
    @Named( "concatDirectors" )
    public String concatDirectors(List<Director> directors) {
        StringBuilder stringBuilder = new StringBuilder();
        if ( !directors.isEmpty() ) {
            for (Director director : directors) {
                if ( stringBuilder.length() != 0 ) {
                    stringBuilder.append( ", " );
                }
                stringBuilder.append( director.getName() );
            }
        }
        return stringBuilder.toString();
    }
}
