package com.recody.recodybackend.catalog.features.searchmovies;

import com.recody.recodybackend.catalog.RecodyCatalogApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@ContextConfiguration(classes = RecodyCatalogApplication.class)
class SearchMoviesTest {
    
    @Autowired SearchMovies client;
    
    @Test
    void test01() {
        // given
        String string = client.searchMoviesString("결심");
        // when
        System.out.println(string);
        
        // then
    }
    
}