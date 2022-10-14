package com.recody.recodybackend.movie.data.productioncountry;

import com.recody.recodybackend.movie.data.MovieBaseEntity;
import com.recody.recodybackend.movie.data.movie.MovieEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "production_country")
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ProductionCountryEntity extends MovieBaseEntity {
    
    @Id
    @GeneratedValue
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "movie_id")
    private MovieEntity movie;
    
    @ManyToOne
    @JoinColumns(value = {
            @JoinColumn(name = "iso_3166_1", referencedColumnName = "iso_3166_1"),
            @JoinColumn(name = "country_name", referencedColumnName = "name")
    })
    private CountryEntity country;
    
    
    @Override
    public String toString() {
        return "[{\"ProductionCountryEntity\":{" + "\"id\":" + id + ", \"movie\":" + movie + ", \"country\":" + country + "}}, " + super.toString() + "]";
    }
}
