package com.recody.recodybackend.movie.data.movie;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.recody.recodybackend.movie.data.movie.QMovieEntity.movieEntity;

@Repository
@RequiredArgsConstructor
@Slf4j
class MovieQueryRepositoryImpl implements MovieQueryRepository{
    
    private final JPAQueryFactory jpaQueryFactory;
    
    @Override
    @Transactional
    public List<MovieEntity> findByTitleLike(String title, Locale locale) {
        log.debug("Searching Movies for title: {}", title);
        List<MovieEntity> result = new ArrayList<>();
        if (locale.equals(Locale.KOREAN)) {
            result = jpaQueryFactory.selectFrom(movieEntity)
                                    .innerJoin(movieEntity.title).fetchJoin()
                                    .where(movieEntity.title.koreanTitle.contains(title))
                                     .limit(20L)
                                     .fetch();
        }
        else if (locale.equals(Locale.ENGLISH)){
            result = jpaQueryFactory.selectFrom(movieEntity)
                                    .innerJoin(movieEntity.title).fetchJoin()
                                    .where(movieEntity.title.englishTitle.contains(title))
                                    .limit(20L)
                                    .fetch();
        }
        log.debug("movie search result: {}", result.size());
        return result;
    }
}
