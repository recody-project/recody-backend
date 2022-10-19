package com.recody.recodybackend.movie.data.people;


import lombok.*;

import javax.persistence.*;

@Entity(name = "MoviePerson")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "movie_person")
@Builder
@Getter
public class MoviePersonEntity {
    
    @Id
    @GeneratedValue
    private Long id;
    
    @Column(name = "tmdb_id", unique = true)
    private Integer tmdbId;
    
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "name_id", nullable = false, foreignKey = @ForeignKey(name = "person_contains_person_name_id"))
    private MoviePersonNameEntity name;
    
    @Column(name = "profile_path")
    private String profilePath;
    
    public void setName(MoviePersonNameEntity name) {
        this.name = name;
    }
    
    @Override
    public String toString() {
        return "{\"MoviePersonEntity\":{" + "\"id\":" + id + ", \"tmdbId\":" + tmdbId + ", \"name\":" + name + ", \"profilePath\":" + ((profilePath != null) ? ("\"" + profilePath + "\"") : null) + "}}";
    }
}
