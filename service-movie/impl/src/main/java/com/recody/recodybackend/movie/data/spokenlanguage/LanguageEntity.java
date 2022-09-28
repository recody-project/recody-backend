package com.recody.recodybackend.movie.data.spokenlanguage;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class LanguageEntity {
    
    @Id
    @Column(name = "iso_639_1")
    private String id;
    
    private String name;
}
