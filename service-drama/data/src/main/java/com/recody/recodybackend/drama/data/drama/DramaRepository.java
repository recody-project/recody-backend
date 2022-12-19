package com.recody.recodybackend.drama.data.drama;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DramaRepository extends JpaRepository<DramaEntity, String>, DramaQueryRepository {

}
