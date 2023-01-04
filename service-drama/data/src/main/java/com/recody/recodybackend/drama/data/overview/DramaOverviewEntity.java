package com.recody.recodybackend.drama.data.overview;

import com.recody.recodybackend.drama.data.drama.DramaEntity;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Table( name = "drama_overview" )
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class DramaOverviewEntity {
    
    @Id
    @GeneratedValue
    private Long id;
    
    @JoinColumn(name = "drama_id",
                nullable = false,
                unique = true,
                foreignKey = @ForeignKey(name = "overview_contains_drama_id"))
    @OneToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @Setter
    private DramaEntity drama;
    
    @Setter
    private String englishOverview;
    
    @Setter
    private String koreanOverview;
    
    @Override
    public String toString() {
        return "{\"DramaOverviewEntity\":{"
               + "\"id\":" + id
               + ", \"englishOverview\":" + ((englishOverview != null) ? ("\"" + englishOverview + "\"") : null)
               + ", \"koreanOverview\":" + ((koreanOverview != null) ? ("\"" + koreanOverview + "\"") : null)
               + "}}";
    }
}
