package com.recody.recodybackend.drama.data.people;

import com.recody.recodybackend.drama.data.drama.DramaEntity;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Table(name = "drama_actor", uniqueConstraints = {
        @UniqueConstraint(name = "drama_and_person_pair_should_be_unique",
                          columnNames = {"drama_id", "person_id"})
})
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class DramaActorEntity {
    
    @Id
    @GeneratedValue
    private Long id;
    
    @JoinColumn( name = "drama_id",
                 nullable = false,
                 foreignKey = @ForeignKey(name = "drama_actor_contains_drama_id"))
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private DramaEntity drama;
    
    @JoinColumn( name = "person_id",
                 nullable = false,
                 foreignKey = @ForeignKey(name = "drama_actor_contains_person_id"))
    @ManyToOne(fetch = FetchType.LAZY)
    private DramaPersonEntity person;
    
    @Override
    public String toString() {
        return "{\"DramaActorEntity\":{"
               + "\"id\":" + id
               + ", \"person\":" + person
               + "}}";
    }
}
