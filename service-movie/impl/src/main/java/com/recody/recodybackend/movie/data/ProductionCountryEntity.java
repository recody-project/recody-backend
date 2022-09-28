package com.recody.recodybackend.movie.data;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class ProductionCountryEntity {
    
    @Id
    @Column(name = "iso_3166_1")
    private String id;
    
    private String name;
    
    @Override
    public String toString() {
        return "{\"ProductionCountryEntity\":{" + "\"id\":" + ((id != null) ? ("\"" + id + "\"") : null) + ", \"name\":" + ((name != null) ? ("\"" + name + "\"") : null) + "}}";
    }
}
