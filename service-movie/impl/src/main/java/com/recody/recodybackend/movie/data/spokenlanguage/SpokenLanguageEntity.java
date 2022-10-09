package com.recody.recodybackend.movie.data.spokenlanguage;

import com.recody.recodybackend.movie.data.MovieBaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@Table(name = "spoken_language")
public class SpokenLanguageEntity extends MovieBaseEntity {
    
    @Id
    @Column(name = "iso_639_1")
    private String id;
    
    private String name;
}
