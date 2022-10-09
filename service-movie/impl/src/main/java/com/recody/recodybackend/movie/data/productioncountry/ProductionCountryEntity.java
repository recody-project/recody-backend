package com.recody.recodybackend.movie.data.productioncountry;

import com.recody.recodybackend.movie.data.MovieBaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@Table(name = "production_country")
public class ProductionCountryEntity extends MovieBaseEntity {
    
    @Id
    @Column(name = "iso_3166_1")
    private String id;
    
    private String name;
    
    @Override
    public String toString() {
        return "{\"ProductionCountryEntity\":{" + "\"id\":" + ((id != null) ? ("\"" + id + "\"") : null) + ", \"name\":" + ((name != null) ? ("\"" + name + "\"") : null) + "}}";
    }
}
