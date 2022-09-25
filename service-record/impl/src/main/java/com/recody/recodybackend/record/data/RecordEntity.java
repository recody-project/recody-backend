package com.recody.recodybackend.record.data;

import com.recody.recodybackend.record.features.generaterecordid.CustomSequenceIdGenerator;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.util.Objects;

@Entity
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class RecordEntity extends RecordBaseEntity {
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
    
    @Column(nullable = false, updatable = false)
    @NonNull
    private String contentId;
    
    @Column(nullable = false, updatable = false)
    @NonNull
    private Long userId;
    
    @Setter
    private String title;
    
    @Setter
    private String note;
    
    @Setter
    private boolean completed;
    
    @Builder.Default
    private Integer nth = 1;
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RecordEntity)) return false;
        RecordEntity that = (RecordEntity) o;
        return Objects.equals(getRecordId(), that.getRecordId());
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(getRecordId());
    }
    
    @Override
    public String toString() {
        return "[{\"RecordEntity\":{" + "\"recordId\":" + ((recordId != null) ? ("\"" + recordId + "\"") : null) + ", \"contentId\":" + ((contentId != null) ? ("\"" + contentId + "\"") : null) + ", \"userId\":" + userId + ", \"note\":" + ((note != null) ? ("\"" + note + "\"") : null) + "}}, " + super.toString() + "]";
    }
}
