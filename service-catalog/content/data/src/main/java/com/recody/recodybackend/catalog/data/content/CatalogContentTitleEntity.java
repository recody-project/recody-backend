package com.recody.recodybackend.catalog.data.content;

import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity(name = "content_title")
@Table(name = "content_title")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class CatalogContentTitleEntity {
    
    @Id
    @GeneratedValue
    private Long id;
    
    @JoinColumn( name = "content_root_id",
                 nullable = false,
                 foreignKey = @ForeignKey(name = "title_contains_content_id"))
    @OneToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @Setter
    private CatalogContentEntity content;
    
    private String koreanTitle;
    
    private String englishTitle;
    
    @Override
    public String toString() {
        return "{\"CatalogContentTitleEntity\":{"
               + "\"id\":" + id
               + ", \"koreanTitle\":" + ((koreanTitle != null) ? ("\"" + koreanTitle + "\"") : null)
               + ", \"englishTitle\":" + ((englishTitle != null) ? ("\"" + englishTitle + "\"") : null)
               + "}}";
    }
}
