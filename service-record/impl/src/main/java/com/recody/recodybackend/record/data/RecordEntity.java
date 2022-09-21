package com.recody.recodybackend.record.data;

import com.recody.recodybackend.record.features.generaterecordid.CustomSequenceIdGenerator;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class RecordEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "record_seq")
    @GenericGenerator(
            name = "record_seq",
            strategy = "com.recody.recodybackend.record.features.generaterecordid.CustomSequenceIdGenerator",
            parameters = {
                    @Parameter(name = CustomSequenceIdGenerator.INCREMENT_PARAM, value = "50"), // high-low 최적화
                    @Parameter(name = CustomSequenceIdGenerator.VALUE_PREFIX_PARAMETER, value = "rec-"),
                    @Parameter(name = CustomSequenceIdGenerator.NUMBER_FORMAT_PARAMETER, value = "%d") })
    private String recordId;
    @Column(nullable = false)
    private String contentId;
    @Column(nullable = false)
    private Long userId;
    
    private String note;
    
    @Override
    public String toString() {
        return "{\"RecordEntity\":{" + "\"recordId\":" + ((recordId != null) ? ("\"" + recordId + "\"") : null) + ", \"contentId\":" + ((contentId != null) ? ("\"" + contentId + "\"") : null) + ", \"userId\":" + userId + ", \"note\":" + ((note != null) ? ("\"" + note + "\"") : null) + "}}";
    }
}
