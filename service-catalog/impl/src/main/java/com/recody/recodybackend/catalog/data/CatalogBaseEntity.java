package com.recody.recodybackend.catalog.data;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@Getter
abstract class CatalogBaseEntity {
    @CreatedDate
    private LocalDateTime createdAt;
    
    @LastModifiedDate
    private LocalDateTime lastModifiedAt;
    
    @Override
    public String toString() {
        return "{\"RecordBaseEntity\":{" + "\"createdAt\":" + ((createdAt != null) ? ("\"" + createdAt + "\"") : null) + ", \"lastModifiedAt\":" + ((lastModifiedAt != null) ? ("\"" + lastModifiedAt + "\"") : null) + "}}";
    }
}
