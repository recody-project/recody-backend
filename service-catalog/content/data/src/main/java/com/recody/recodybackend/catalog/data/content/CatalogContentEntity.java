package com.recody.recodybackend.catalog.data.content;

import com.recody.recodybackend.catalog.data.CatalogBaseEntity;
import com.recody.recodybackend.catalog.data.category.CategoryEntity;
import com.recody.recodybackend.commonbootutils.data.CustomSequenceIdGenerator;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table( name = "catalog_content" )
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor( access = AccessLevel.PROTECTED )
public class CatalogContentEntity extends CatalogBaseEntity {
    
    /**
     * Catalog 서비스에서 부여하는 고유 ID 이다. 카테고리와 관련없이 고유한 형태로 부여된다.
     * 이 ID 는 Category 와 contentId 의 유일한 조합과 같은 경우의 수여야 한다.
     */
    @Id
    @GeneratedValue( generator = "content_seq" )
    @GenericGenerator( name = "content_seq",
                       strategy = "com.recody.recodybackend.commonbootutils.data.CustomSequenceIdGenerator",
                       parameters = {@Parameter( name = CustomSequenceIdGenerator.INCREMENT_PARAM, value = "50" ),
                                     @Parameter( name = CustomSequenceIdGenerator.VALUE_PREFIX_PARAMETER, value = "con-" ),
                                     @Parameter( name = CustomSequenceIdGenerator.NUMBER_FORMAT_PARAMETER, value = "%d" )} )
    private String id;
    /**
     * 작품의 기본 카테고리이다.
     */
    @ManyToOne( fetch = FetchType.EAGER )
    @JoinColumn( name = "category_id", nullable = false )
    private CategoryEntity category;
    /**
     * 작품의 카테고리별로 부여된 ID 이다. 예를 들어 영화의 경우, mov-1 과 같은 형식을 띈다.
     */
    @Column( nullable = false, unique = true )
    private String contentId;
    private String imageUrl;
    @OneToOne( mappedBy = "content", cascade = CascadeType.ALL )
    private CatalogContentTitleEntity title;
    
    public CatalogContentEntity(CategoryEntity category, String contentId) {
        this.category = category;
        this.contentId = contentId;
    }
    
    public void setTitle(CatalogContentTitleEntity title) {
        this.title = title;
        title.setContent( this );
    }
    
    
    @Override
    public String toString() {
        return "{\"CatalogContentEntity\":{" + "\"id\":" + ((id != null) ? ("\"" + id + "\"") : null) + ", \"category\":" + category + ", \"contentId\":" + ((contentId != null) ? ("\"" + contentId + "\"") : null) + ", \"imageUrl\":" + ((imageUrl != null) ? ("\"" + imageUrl + "\"") : null) + ", \"title\":" + ((title != null) ? ("\"" + title + "\"") : null) + "}}";
    }
    
    @Override
    public boolean equals(Object o) {
        if ( this == o ) return true;
        if ( !(o instanceof CatalogContentEntity) ) return false;
        CatalogContentEntity that = (CatalogContentEntity) o;
        return Objects.equals( getId(), that.getId() );
    }
    
    @Override
    public int hashCode() {
        return Objects.hash( getId() );
    }
}
