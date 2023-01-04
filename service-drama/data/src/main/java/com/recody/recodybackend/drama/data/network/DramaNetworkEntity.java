package com.recody.recodybackend.drama.data.network;

import com.recody.recodybackend.drama.data.drama.DramaEntity;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Table( name = "drama_network" )
@AllArgsConstructor
@NoArgsConstructor( access = AccessLevel.PROTECTED )
@Getter
@Builder
public class DramaNetworkEntity {
    
    @Id
    @GeneratedValue
    private Long id;
    
    @JoinColumn( name = "drama_id",
                 nullable = false,
                 unique = true,
                 foreignKey = @ForeignKey(name = "network_contains_drama_id"))
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private DramaEntity drama;
    
    @JoinColumn( name = "network_info_id",
                 nullable = false,
                 foreignKey = @ForeignKey(name = "network_contains_network_code_id"))
    @ManyToOne( fetch = FetchType.LAZY)
    private DramaNetworkInfoEntity networkInfo;
    
    @Override
    public String toString() {
        return "{\"DramaNetworkEntity\":{"
               + "\"id\":" + id
               + ", \"networkInfo\":" + networkInfo
               + "}}";
    }
}
