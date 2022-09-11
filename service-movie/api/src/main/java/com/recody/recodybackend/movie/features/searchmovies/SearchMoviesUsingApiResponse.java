package com.recody.recodybackend.movie.features.searchmovies;

import com.recody.recodybackend.movie.general.MovieSource;

import java.util.List;
import java.util.Map;

/*
 * API 를 통해 검색하여 받아온 결과.
 * 여러개의 영화 정보가 들어있다.
 * 따로 가공되지 않은 상태.
 * 요청시 결과를 꺼내주고, 스스로 의미있는 형태로 가공하지 않는다.
 * 어떤 요소든 id 와 매핑하여 반환할 수 있다.
 * 1회성으로 사용되는 데이터. 사용자에게 반환할 SearchMovieResponse 가 정해지면 버린다. */
public interface SearchMoviesUsingApiResponse {
    /*
     * 검색 결과의 영화 장르들을 반환한다.
     * 하나의 영화는 여러개의 장르를 가질 수 있다. */
    Map<Integer, List<Integer>> getGenreIdsMap();
    
    /*
     * 검색 결과에서 영화 id 들을 반환한다.
     * 이 id 는 영화의 root id 를 찾는 데에 사용된다. */
    List<Integer> getMovieIds();
    
    /*
     * 항상 검색 결과는 어떤 소스에서 왔는지 정보를 포함한다.
     * 필요시 소스가 어디인지 반환한다.
     * 이 소스 정보는 장르를 찾거나 root id 를 찾는데에 참고한다.*/
    MovieSource getContentSource();
    
    /*
    * 아래 getter 들은 검색 결과 중에 n 번째 검색결과의 각 필드를 반환하는 메서드들이다. */
    Integer getMovieId(int n);
    
    String getOriginalLanguage(int n);
    
    String getOriginalTitle(int n);
    
    String getOverview(int n);
    
    String getPosterPath(int n);
    
    String getReleaseDate(int n);
    
    String getTitle(int n);
    
    /*
    * 검색 결과의 개수를 반환한다. */
    int size();
}
