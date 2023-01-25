package com.recody.recodybackend.drama.data.drama;

import com.recody.recodybackend.commonbootutils.data.CustomSequenceIdGenerator;
import com.recody.recodybackend.drama.data.genre.DramaGenreEntity;
import com.recody.recodybackend.drama.data.network.DramaNetworkEntity;
import com.recody.recodybackend.drama.data.overview.DramaOverviewEntity;
import com.recody.recodybackend.drama.data.people.DramaActorEntity;
import com.recody.recodybackend.drama.data.people.DramaDirectorEntity;
import com.recody.recodybackend.drama.data.title.DramaTitleEntity;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table( name = "drama" )
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DramaEntity {
    
    @Id
    @GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "drama_seq" )
    @GenericGenerator( name = "drama_seq",
                       strategy = "com.recody.recodybackend.commonbootutils.data.CustomSequenceIdGenerator",
                       parameters = {
                               @Parameter( name = CustomSequenceIdGenerator.INCREMENT_PARAM, value = "50" ),
                               @Parameter( name = CustomSequenceIdGenerator.VALUE_PREFIX_PARAMETER,
                                           value = "dra-" ),
                               @Parameter( name = CustomSequenceIdGenerator.NUMBER_FORMAT_PARAMETER,
                                           value = "%d" )} )
    private String id;
    
    @OneToOne( mappedBy = "drama", cascade = CascadeType.ALL )
    private DramaTitleEntity title;
    
    @OneToOne( mappedBy = "drama", cascade = CascadeType.ALL)
    private DramaOverviewEntity overview;
    
    private String imageUrl;
    
    @Column( name = "tmdb_id", unique = true, nullable = false, updatable = false )
    private Integer tmdbId;
    
    private String firstAirDate;
    
    private Integer numberOfEpisodes;
    
    @OneToMany( mappedBy = "drama" )
    @Builder.Default
    private List<DramaActorEntity> actors = new ArrayList<>();
    
    @OneToMany( mappedBy = "drama" )
    @Builder.Default
    private List<DramaDirectorEntity> directors = new ArrayList<>();
    
    @OneToMany( mappedBy = "drama" )
    @Builder.Default
    private List<DramaNetworkEntity> networks = new ArrayList<>();
    
    @OneToMany( mappedBy = "drama" )
    @Builder.Default
    private List<DramaGenreEntity> genres = new ArrayList<>();
    
    public void setTitle(DramaTitleEntity title) {
        title.setDrama( this );
        this.title = title;
    }
    
    @Override
    public String toString() {
        return "{\"DramaEntity\":{"
               + "\"id\":" + ((id != null) ? ("\"" + id + "\"") : null)
               + ", \"title\":" + title
               + ", \"overview\":" + overview
               + ", \"imageUrl\":" + ((imageUrl != null) ? ("\"" + imageUrl + "\"") : null)
               + ", \"tmdbId\":" + tmdbId
               + ", \"firstAirDate\":" + ((firstAirDate != null) ? ("\"" + firstAirDate + "\"") : null)
               + ", \"numberOfEpisodes\":" + numberOfEpisodes
               + "}}";
    }
}
