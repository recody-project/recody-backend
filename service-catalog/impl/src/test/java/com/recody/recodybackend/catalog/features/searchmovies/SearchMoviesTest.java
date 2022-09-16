package com.recody.recodybackend.catalog.features.searchmovies;

import com.recody.recodybackend.catalog.RecodyCatalogApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.*;

/*
* 이 테스트는 Movie 앱이 떠있다는 가정이 있어야 한다. */
@SpringBootTest
@ActiveProfiles("test")
@ContextConfiguration(classes = RecodyCatalogApplication.class)
class SearchMoviesTest {
    
    @Autowired SearchMovies client;
    
    @Test
    void test01() {
    }
    
}