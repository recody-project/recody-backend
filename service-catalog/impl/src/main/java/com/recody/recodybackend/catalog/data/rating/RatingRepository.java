package com.recody.recodybackend.catalog.data.rating;

import com.recody.recodybackend.catalog.data.LookupId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RatingRepository extends JpaRepository<RatingEntity, LookupId> {

}
