package com.recody.recodybackend.drama.data.genre;

import com.recody.recodybackend.commonbootutils.data.CustomSequenceIdGenerator;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;

@Entity
@Table( name = "drama_genre_code" )
@AllArgsConstructor
@NoArgsConstructor( access = AccessLevel.PROTECTED )
@Getter
@Builder
public class DramaGenreCodeEntity {
    
    @Id
    @GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "drama_genre_seq")
    @GenericGenerator(
            name = "drama_genre_seq",
            strategy = "com.recody.recodybackend.commonbootutils.data.CustomSequenceIdGenerator",
            parameters = {
                    @Parameter( name = CustomSequenceIdGenerator.INCREMENT_PARAM, value = "50"),
                    @Parameter( name = CustomSequenceIdGenerator.VALUE_PREFIX_PARAMETER, value = "dg-"),
                    @Parameter( name = CustomSequenceIdGenerator.NUMBER_FORMAT_PARAMETER, value = "%d") })
    private String id;
    
    @Column(unique = true, updatable = false, nullable = false)
    private Integer tmdbGenreId;
    
    @Setter
    private String tmdbEnglishName;
    
    @Setter
    private String tmdbKoreanName;
    
    @Override
    public String toString() {
        return "{\"DramaGenreCodeEntity\":{"
               + "\"id\":" + id
               + ", \"tmdbGenreId\":" + tmdbGenreId
               + ", \"tmdbEnglishName\":" + ((tmdbEnglishName != null) ? ("\"" + tmdbEnglishName + "\"") : null)
               + ", \"tmdbKoreanName\":" + ((tmdbKoreanName != null) ? ("\"" + tmdbKoreanName + "\"") : null)
               + "}}";
    }
}
