package com.recody.recodybackend.movie.data.movie;

import com.recody.recodybackend.movie.data.ProductionCountryEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter@Setter
public class MovieProductionCountryRelation {
    
    @Id
    @GeneratedValue
    private Long id;
    
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "movie_id")
    private MovieEntity movie;
    
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "production_country_id")
    private ProductionCountryEntity productionCountry;
    
    @Override
    public String toString() {
        return "{\"MovieProductionCountryRelation\":{" + "\"id\":" + id + ", \"movie\":" + movie + ", \"productionCountry\":" + productionCountry + "}}";
    }
}
