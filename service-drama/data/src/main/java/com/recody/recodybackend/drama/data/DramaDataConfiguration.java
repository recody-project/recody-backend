package com.recody.recodybackend.drama.data;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Configuration
class DramaDataConfiguration {
    
    @PersistenceContext
    private EntityManager entityManager;
    
    @Bean
    @ConditionalOnMissingBean(value = JPAQueryFactory.class)
    JPAQueryFactory jpaQueryFactory(){
        return new JPAQueryFactory( entityManager );
    }
}
