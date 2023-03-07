package com.recody.recodybackend.book.features.fetchbookdetail;

import com.recody.recodybackend.book.naver.Naver;
import com.recody.recodybackend.book.naver.NaverBookSearchResponse;
import com.recody.recodybackend.book.naver.detail.NaverBookDetail;
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
import java.util.Objects;

@Component
@RequiredArgsConstructor
@Slf4j
class DefaultFetchBookDetailHandler implements FetchBookDetailHandler<NaverBookDetail> {

    private final RestTemplate restTemplate = new RestTemplate();

    private final WebClient webClient = WebClient.builder().baseUrl(Naver.Naver_baseUrl).build();
    @Value("${book.naver.api-id}")
    private String apiID;
    @Value("${book.naver.api-key}")
    private String apiKey;

    @Override
    public NaverBookDetail handle(FetchBookDetail query) {

        URI url = makeUrl(query.getIsbn());
        HttpHeaders headers = makeHeader();
        RequestEntity<Void> RE = new RequestEntity<>(headers, HttpMethod.GET, url);
        NaverBookDetail naverBookDetail;
        try {

            naverBookDetail = restTemplate.exchange(RE, NaverBookDetail.class).getBody();
            Objects.requireNonNull(naverBookDetail);
        } catch (RestClientException exception4xx) {
            log.warn("exception4xx: {}", exception4xx.getMessage());
            throw new RuntimeException();
        }

        return naverBookDetail;

//        return webClient.get()
//                .uri(uriBuilder -> uriBuilder.queryParam("d_isbn", query.getIsbn())
//                        .build())
//                .headers(httpHeaders -> {
//                    httpHeaders.set(Naver.API_ID_PARAM_NAME, apiID);
//                    httpHeaders.set(Naver.API_KEY_PARAM_NAME, apiKey);
//                })
//                .retrieve()
//                .bodyToMono(NaverBookDetail.class)
//                .block();
    }

    private HttpHeaders makeHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.set(Naver.API_ID_PARAM_NAME, apiID);
        headers.set(Naver.API_KEY_PARAM_NAME, apiKey);
        return headers;
    }

    private URI makeUrl(String isbn) {
        return UriComponentsBuilder.fromUriString(Naver.Naver_baseUrl)
                .queryParam(Naver.NAVER_QUERY_PARAM_NAME, isbn)
                .encode()
                .build()
                .toUri();

    }
}
