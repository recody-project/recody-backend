package com.recody.recodybackend.record.data.rating;

import com.recody.recodybackend.record.data.LookupId;
import com.recody.recodybackend.record.data.content.RecordContentEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Entity(name = "RecordRating")
@Table(name = "record_rating")
@IdClass(LookupId.class)
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
@Getter
public class RecordRatingEntity {
    
    @Id
    private Long userId;
    
    /**
     * 작품과 매핑된다. 하나의 작품에 한 유저는 1개의 평점만 남길 수 있다.
     */
    @Id
    @ManyToOne
    @JoinColumn(name = "content_id",
                nullable = false,
                foreignKey = @ForeignKey(name = "record_rating_contains_record_content_id"))
    private RecordContentEntity content;
    
    @Column(nullable = false)
    @Setter
    private Integer score;
    
    @Override
    public String toString() {
        return "{\"RecordRatingEntity\":{" + "\"userId\":" + userId + ", \"content\":" + content + ", \"score\":" + score + "}}";
    }
}
