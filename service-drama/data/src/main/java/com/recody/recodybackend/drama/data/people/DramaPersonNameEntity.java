package com.recody.recodybackend.drama.data.people;

import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Table( name = "drama_person_name" )
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class DramaPersonNameEntity {
    
    @Id
    @GeneratedValue
    private Long id;
    
    @JoinColumn( name = "person_id",
                 nullable = false,
                 unique = true,
                 foreignKey = @ForeignKey( name = "drama_person_name_contains_person_id" ) )
    @OneToOne( fetch = FetchType.LAZY )
    @OnDelete( action = OnDeleteAction.CASCADE )
    private DramaPersonEntity person;
    
    private String originalName;
    
    private String englishName;
    
    private String koreanName;
    
    @Override
    public String toString() {
        return "{\"DramaPersonNameEntity\":{"
               + "\"id\":" + id
               + ", \"originalName\":" + ((originalName != null) ? ("\"" + originalName + "\"") : null)
               + ", \"englishName\":" + ((englishName != null) ? ("\"" + englishName + "\"") : null)
               + ", \"koreanName\":" + ((koreanName != null) ? ("\"" + koreanName + "\"") : null)
               + "}}";
    }
}
