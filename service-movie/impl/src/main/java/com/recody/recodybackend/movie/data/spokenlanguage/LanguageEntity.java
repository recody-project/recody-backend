package com.recody.recodybackend.movie.data.spokenlanguage;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Table(name = "language_code")
public class LanguageEntity {
    
    
    @Id
    @Column(name = "iso_639_1")
    private String id;
    
    private String name;
    
}
