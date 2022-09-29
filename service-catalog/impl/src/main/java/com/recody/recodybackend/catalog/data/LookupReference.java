package com.recody.recodybackend.catalog.data;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
public class LookupReference {
    
    @EmbeddedId
    private LookupId lookupId;
    
    @Column(nullable = false, updatable = false)
    private UUID referenceId = UUID.randomUUID();
    
    public LookupReference(LookupId lookupId) {
        this.lookupId = lookupId;
    }
}
