package com.recody.recodybackend.catalog.features.personalize;

import com.recody.recodybackend.catalog.PersonalizedMovieDetail;
import com.recody.recodybackend.catalog.CatalogMovieDetail;
import com.recody.recodybackend.common.contents.Genre;
import com.recody.recodybackend.movie.MovieGenre;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
class MovieDetailPersonalizer
        implements ContentDetailPersonalizer<CatalogMovieDetail, PersonalizedMovieDetail> {
    
    private final PersonalizedMovieMapper personalizedMovieMapper;
    
    @Override
    public PersonalizedMovieDetail personalize(CatalogMovieDetail content, Long userId) {
        PersonalizedMovieDetail personalizedMovieDetail = personalizedMovieMapper.map(content);
        // TODO 개인화된 장르가 있는지 확인한다. 없으면 content 의 장르를 그대로 넣는다.
        
        List<MovieGenre> movieGenres = content.getGenres();
        List<Genre> genres = List.copyOf(movieGenres); // unmodifiableList
        personalizedMovieDetail.setGenres(genres);
        personalizedMovieDetail.setCategory(content.getCategory());
        personalizedMovieDetail.setPersonalizedUserId(userId);
        log.info("영화 정보 개인화 완료");
        return personalizedMovieDetail;
    }
}
