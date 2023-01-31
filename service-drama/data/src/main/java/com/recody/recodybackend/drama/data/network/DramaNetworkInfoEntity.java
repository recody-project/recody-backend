package com.recody.recodybackend.drama.data.network;

import lombok.*;

import javax.persistence.*;

@Entity
@Table( name = "drama_network_info" )
@AllArgsConstructor
@NoArgsConstructor( access = AccessLevel.PROTECTED )
@Getter
@Builder
public class DramaNetworkInfoEntity {
    
    @Id
    @GeneratedValue
    private Long id;
    
    @Column(name = "tmdb_id", nullable = false)
    private Integer tmdbId;
    
    @Column( name = "name", nullable = false )
    private String name;
    
    @Override
    public String toString() {
        return "{\"DramaNetworkInfoEntity\":{"
               + "\"id\":" + id
               + ", \"tmdbId\":" + tmdbId
               + ", \"name\":" + ((name != null) ? ("\"" + name + "\"") : null)
               + "}}";
    }
}
