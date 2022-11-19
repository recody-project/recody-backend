package com.recody.recodybackend.catalog.data.rating;

import com.recody.recodybackend.catalog.data.CatalogBaseEntity;
import com.recody.recodybackend.catalog.data.LookupId;
import com.recody.recodybackend.catalog.data.content.CatalogContentEntity;
import com.recody.recodybackend.catalog.data.user.CatalogUserEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

/**
 *
 */
@Entity(name = "rating")
@Table(name = "rating")
@IdClass(LookupId.class)
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
@Getter
public class RatingEntity extends CatalogBaseEntity {
    
    @Id
    @ManyToOne
    @JoinColumn(name = "user_id",
                foreignKey = @ForeignKey(name = "rating_contains_user_id"))
    private CatalogUserEntity user;
    
    /**
     * 작품과 매핑된다. 하나의 작품에 한 유저는 1개의 평점만 남길 수 있다.
     */
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "content_id",
                foreignKey = @ForeignKey(name = "rating_contains_content_id"))
    private CatalogContentEntity content;
    @Column(nullable = false)
    @Setter
    private Integer score;
    
    @Override
    public String toString() {
        return "[{\"RatingEntity\":{"
               + "\"user\":" + user
               + ", \"score\":" + score
               + "}}, " + super.toString() + "]";
    }
}
