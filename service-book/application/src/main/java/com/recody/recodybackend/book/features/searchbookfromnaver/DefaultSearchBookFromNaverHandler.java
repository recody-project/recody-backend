package com.recody.recodybackend.book.features.searchbookfromnaver;


import com.recody.recodybackend.book.features.searchbooks.SearchBooks;
import com.recody.recodybackend.book.naver.NaverBookSearchResponse;
import com.recody.recodybackend.book.searchbooks.dto.NaverBookSearchNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Objects;


@Component
@RequiredArgsConstructor
@Slf4j
class DefaultSearchBookFromNaverHandler implements SearchBookFromNaverHandler<NaverBookSearchResponse> {

    private static final String baseUrl = "https://openapi.naver.com/v1/search/book.json";
    private static final String NAVER_QUERY_PARAM_NAME = "query";
    private static final String NAVER_DISPLAY_PARAM_NAME = "display";
    private static final String NAVER_START_PARAM_NAME = "start";
    private static final String API_ID_PARAM_NAME = "X-Naver-Client-Id";
    private static final String API_KEY_PARAM_NAME = "X-Naver-Client-Secret";
    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${book.naver.api-id}")
    private String apiID;
    @Value("${book.naver.api-key}")
    private String apiKey;
    private final WebClient webClient = WebClient.builder()
            .baseUrl( baseUrl ).build();


//    @Override
//    public NaverBookSearchResponse handle(SearchBookFromNaver query) {
//        log.debug("handling command: {}", query);
//        return webClient.get()
//                .uri(uriBuilder -> uriBuilder.queryParam("query", query.getQueryText())
//                        .queryParam("display", query.getDisplay())
//                        .queryParam("start", query.getStart())
//                        .build())
//                .headers(httpHeaders -> {
//                    httpHeaders.set(API_ID_PARAM_NAME, apiID);
//                    httpHeaders.set(API_KEY_PARAM_NAME, apiKey);
//                })
//                .retrieve()
//                .bodyToMono(NaverBookSearchResponse.class)
//                .block();
//
//    }

    @Override
    public NaverBookSearchResponse handle(SearchBookFromNaver query) {
        log.debug("handling command: {}", query.getQueryText());

        URI url = makeUrl(query.getQueryText(), query.getDisplay(), query.getStart());
        HttpHeaders headers = makeHeader();
        RequestEntity<Void> RE = new RequestEntity<>(headers, HttpMethod.GET, url);
        NaverBookSearchResponse response;
        try {

            response = restTemplate.exchange(RE, NaverBookSearchResponse.class).getBody();
            Objects.requireNonNull(response);
        } catch (RestClientException exception4xx) {
            log.warn("exception4xx: {}", exception4xx.getMessage());
            throw new RuntimeException();
        }

        return response;

    }

//    @Override
//    public List<NaverBookSearchNode> handle(SearchBooks command) {
//        log.debug("handling command: {}", command);
//        String bookName = command.getKeyword();
//        Integer display = command.getDisplay();
//        Integer start = command.getStart();
//        URI url = makeUrl(bookName, display, start);
//        HttpHeaders headers = makeHeader();
//        RequestEntity<Void> RE = new RequestEntity<>(headers, HttpMethod.GET, url);
////        RequestEntity<Void> RE = RequestEntity.get(headers, url).build();
//        NaverBookSearchResponse response;
//        try {
//            response = restTemplate.exchange(RE, NaverBookSearchResponse.class).getBody();
//            Objects.requireNonNull(response);
//        } catch (RestClientException exception4xx) {
//            log.warn("exception4xx: {}", exception4xx.getMessage());
//            throw new RuntimeException();
//        }
//        List<NaverBookSearchNode> naverBooks = response.getItems();
//
////        List<NaverBookSearchNode> naverBooks = response.getItems();
//        log.debug("네이버에서 책 검색 결과를 가져왔습니다. size: {}", naverBooks.size());
//
//        return naverBooks;
//    }

    private HttpHeaders makeHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.set(API_ID_PARAM_NAME, apiID);
        headers.set(API_KEY_PARAM_NAME, apiKey);
        return headers;
    }

    private URI makeUrl(String bookName, Integer display, Integer start) {
        return UriComponentsBuilder.fromUriString(baseUrl)
                .queryParam(NAVER_QUERY_PARAM_NAME, bookName)
                .queryParam(NAVER_DISPLAY_PARAM_NAME, display)
                .queryParam(NAVER_START_PARAM_NAME, start)
                .encode()
                .build()
                .toUri();

    }


}
