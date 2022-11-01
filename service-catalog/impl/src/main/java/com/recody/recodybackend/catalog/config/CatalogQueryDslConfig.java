package com.recody.recodybackend.catalog.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Configuration
class CatalogQueryDslConfig {
    
    @PersistenceContext
    private EntityManager entityManager;
    
    
    @Bean
    @ConditionalOnMissingBean( JPAQueryFactory.class )
    public JPAQueryFactory jpaQueryFactory() {
        return new JPAQueryFactory(entityManager);
    }
    
}
