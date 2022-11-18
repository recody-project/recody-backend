package com.recody.recodybackend.movie.features.searchmovies;

import org.springframework.data.domain.Page;

import javax.validation.Valid;
import java.util.List;

/*
* 1. Api 에서 받아온 응답을 사용하여 유저에게 SearchMoviesResult 를 반환한다.
* 2. 이 과정에서 장르 정보를 장르 id, 이름을 가져와 세팅한다.
* 3. 이 과정에서 root id 에 대한 정보를 세팅한다.
* 4. 요청한 언어가 무엇인지 명시해준다.
*
* 위 정보를 가져오는 시점이 서로 다를 수 있다.
* 따라서 따로 mapper 를 구현해서 장르 정보, rootId 정보를 세팅하는 대로 반환하도록 한다.
* */
public interface SearchMoviesHandler<T> {
    List<T> handle(SearchMovies command);
    
    Page<T> handlePage(@Valid SearchMovies command);
}
