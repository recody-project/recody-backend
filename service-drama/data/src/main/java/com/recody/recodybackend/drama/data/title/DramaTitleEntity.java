package com.recody.recodybackend.drama.data.title;

import com.recody.recodybackend.drama.data.drama.DramaEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Table(name = "drama_title")
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class DramaTitleEntity {
    
    @Id
    private String id;
    
    @JoinColumn(name = "drama_id",
                nullable = false,
                unique = true,
                foreignKey = @ForeignKey(name = "title_contains_drama_id"))
    @OneToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private DramaEntity drama;
    
    private String koreanTitle;
    
    private String englishTitle;
    
    private String originalTitle;
}
