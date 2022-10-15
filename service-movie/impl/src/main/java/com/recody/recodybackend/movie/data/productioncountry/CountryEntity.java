package com.recody.recodybackend.movie.data.productioncountry;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "country")
@AllArgsConstructor
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CountryEntity implements Serializable {
    
    
    
    @Id
    @Column(name = "iso_3166_1")
    private String id;
    
    @Column(name = "name")
    private String name;
    
}
