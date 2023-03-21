package com.recody.recodybackend.insight.data.user;

import lombok.*;

import javax.persistence.*;
import java.time.YearMonth;
import java.util.UUID;

@Entity
@Table( name = "insight_monthly_statistics",
        uniqueConstraints = @UniqueConstraint( columnNames = {
                "user_id", "year_and_month"
        }))
@AllArgsConstructor
@NoArgsConstructor( access = AccessLevel.PROTECTED )
@Getter
@Builder
public class InsightMonthlyStatisticsEntity {
    @Id
    @GeneratedValue(generator = "uuid2")
    public UUID uuid;
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    public InsightUserEntity user;
    
    @Column(name = "year_and_month", columnDefinition = "varchar(10)")
    public YearMonth yearMonth;
    
    /**
     * 이번 달 감상평의 개수
     */
    public Integer recordCount;
    /**
     * 이번 달 감상한 작품의 개수
     */
    public Integer contentCount;
    /**
     * 이번 달 남긴 평점의 평균
     */
    public Float rateAverage;
    /**
     * 이번 달에서 첫번째 감상평을 쓴 작품 Id
     */
    public String firstRecordContentId;
    /**
     * 이번 달에서 가장 긴 감상평의 Id
     */
    public String longestRecordId;
    
    /**
     *  이번 달에서 가장 긴 감상평을 쓴 작품의 Id
     */
    public String longestRecordContentId;
}
