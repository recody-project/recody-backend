package com.recody.recodybackend.drama.data.people;

import lombok.*;

import javax.persistence.*;

@Entity
@Table( name = "drama_person" )
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class DramaPersonEntity {
    
    @Id
    @GeneratedValue
    private Long id;
    
    @Column( name = "tmdb_id", unique = true, nullable = false, updatable = false )
    private Integer tmdbId;
    
    @OneToOne( mappedBy = "person", optional = false, cascade = CascadeType.ALL )
    private DramaPersonNameEntity name;
    
    private String profileUrl;
    
}
