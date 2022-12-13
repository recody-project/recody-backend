package com.recody.recodybackend.movie.data.people;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "MoviePersonName")
@Table(name = "movie_person_name")
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Setter
public class MoviePersonNameEntity {
    @Id
    @GeneratedValue
    private Long id;
    
    private String englishName;
    
    private String koreanName;
    
    @Override
    public String toString() {
        return "{\"MoviePersonNameEntity\":{" + "\"id\":" + id + ", \"englishName\":" + ((englishName != null) ? ("\"" + englishName + "\"") : null) + ", \"koreanName\":" + ((koreanName != null) ? ("\"" + koreanName + "\"") : null) + "}}";
    }
}
