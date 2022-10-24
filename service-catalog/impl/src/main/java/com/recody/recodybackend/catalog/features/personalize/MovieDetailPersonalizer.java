package com.recody.recodybackend.catalog.features.personalize;

import com.recody.recodybackend.catalog.PersonalizedMovieDetail;
import com.recody.recodybackend.catalog.PersonalizedMovieMapper;
import com.recody.recodybackend.movie.MovieDetail;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
class MovieDetailPersonalizer implements ContentDetailPersonalizer<MovieDetail, PersonalizedMovieDetail> {
    
    private final PersonalizedMovieMapper personalizedMovieMapper;
    
    @Override
    public PersonalizedMovieDetail personalize(MovieDetail content, Long userId) {
        PersonalizedMovieDetail personalizedMovieDetail = personalizedMovieMapper.map(content);
        // TODO 개인화된 장르가 있는지 확인한다. 없으면 content 의 장르를 그대로 넣는다.
        
        personalizedMovieDetail.setGenres(content.getGenres());
        personalizedMovieDetail.setCategory(content.getCategory());
        personalizedMovieDetail.setPersonalizedUserId(userId);
        log.info("영화 정보 개인화 완료");
        return personalizedMovieDetail;
    }
}
