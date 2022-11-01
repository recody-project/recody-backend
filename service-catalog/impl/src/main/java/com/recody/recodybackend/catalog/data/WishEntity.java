package com.recody.recodybackend.catalog.data;

import com.recody.recodybackend.catalog.data.content.CatalogContentEntity;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "wish")
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class WishEntity extends CatalogBaseEntity{
    
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Type(type = "uuid-char")
    private UUID id;
    
    /**
     * 1개의 컨텐츠와 대응된다. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "catalog_content_id", nullable = false)
    private CatalogContentEntity catalogContent;
    
    /**
     * 특정 유저에 대해서 위시가 이루어진다. */
    @Column(name = "user_id", nullable = false)
    private Long userId;
    
    @Version
    private Integer version;
    
    @Override
    public String toString() {
        return "[{\"WishEntity\":{" + "\"id\":" + ((id != null) ? ("\"" + id + "\"") : null) + ", \"catalogContent\":" + catalogContent + ", \"userId\":" + userId + "}}, " + super.toString() + "]";
    }
}
