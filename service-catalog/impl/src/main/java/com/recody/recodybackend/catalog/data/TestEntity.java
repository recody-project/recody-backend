package com.recody.recodybackend.catalog.data;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.io.Serializable;
import java.util.UUID;

@Entity
class TestEntity{
    
    @EmbeddedId
    private LookupId lookupId;
    
    @Column(nullable = false)
    private String name;
    
    public TestEntity(LookupId lookupId, String name) {
        this.lookupId = lookupId;
        this.name = name;
    }
    
    public TestEntity() {
    }
    
    public LookupId getLookupId() {
        return lookupId;
    }
    
    public String getName() {
        return name;
    }
    
    @Override
    public String toString() {
        return "{\"TestEntity\":{" + "\"lookupId\":" + lookupId + ", \"name\":" + ((name != null) ? ("\"" + name + "\"") : null) + "}}";
    }
}
