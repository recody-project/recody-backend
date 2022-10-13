package com.recody.recodybackend.catalog.data.rating;

import com.recody.recodybackend.catalog.data.CatalogBaseEntity;
import com.recody.recodybackend.catalog.data.LookupId;
import com.recody.recodybackend.catalog.data.content.CatalogContentEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Entity(name = "rating")
@Table(name = "rating")
@IdClass(LookupId.class)
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
@Getter
public class RatingEntity extends CatalogBaseEntity {
    
    @Id
    private Long userId;
    
    /**
     * 작품과 매핑된다. 하나의 작품에 한 유저는 1개의 평점만 남길 수 있다.
     */
    @Id
    @ManyToOne
    @JoinColumn(name = "content_id")
    private CatalogContentEntity content;
    
    @Column(nullable = false)
    @Setter
    private Integer score;
    
    @Override
    public String toString() {
        return "[{\"RatingEntity\":{" + "\"userId\":" + userId + ", \"content\":" + content + ", \"score\":" + score + "}}, " + super.toString() + "]";
    }
}
