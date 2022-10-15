package com.recody.recodybackend.movie.data.productioncountry;

import com.recody.recodybackend.movie.data.MovieBaseEntity;
import com.recody.recodybackend.movie.data.movie.MovieEntity;
import lombok.*;

import javax.persistence.*;

/**
 * 특정 영화에 제작에 참여한 국가를 의미하는 엔티티입니다.<br>
 * CountryEntity 와 MovieEntity 의 각 테이블 매핑에서 연결 테이블 역할을 합니다.
 */
@Entity(name = "production_country")
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
    @JoinColumn(name = "movie_id",
                nullable = false,
                foreignKey = @ForeignKey(name = "production_country_contains_movie_id"))
    private MovieEntity movie;
    
    @ManyToOne
    @JoinColumns(value = {@JoinColumn(name = "iso_3166_1",
                                      referencedColumnName = "iso_3166_1",
                                      nullable = false),
                          @JoinColumn(name = "country_name",
                                      referencedColumnName = "name",
                                      nullable = false)},
                 foreignKey = @ForeignKey(name = "production_country_contains_country_id_and_country_name"))
    private CountryEntity country;
    
    
    @Override
    public String toString() {
        return "[{\"ProductionCountryEntity\":{" + "\"id\":" + id + ", \"movie\":" + movie + ", \"country\":" + country + "}}, " + super.toString() + "]";
    }
}
