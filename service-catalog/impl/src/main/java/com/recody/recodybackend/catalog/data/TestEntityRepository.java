package com.recody.recodybackend.catalog.data;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TestEntityRepository extends JpaRepository<TestEntity, LookupId> {
    
    Optional<TestEntity> findByLookupId(LookupId lookupId);
}
