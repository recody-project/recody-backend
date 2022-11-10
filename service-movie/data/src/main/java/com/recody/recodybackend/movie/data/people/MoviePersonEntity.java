package com.recody.recodybackend.movie.data.people;


import lombok.*;

import javax.persistence.*;

@Entity(name = "MoviePerson")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "movie_person")
@Builder
@Getter
@Setter
public class MoviePersonEntity {
    
    @Id
    @GeneratedValue
    private Long id;
    
    @Column(name = "tmdb_id", unique = true)
    private Integer tmdbId;
    
    // One to One 이기도 하고 해서 이름은 Eager 로 가져온다. Mapper 에서 이름을 가져오는 경우가 많은데
    // 트랜잭션이 없으면 LazyInitializationException 이 나온다.
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
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
