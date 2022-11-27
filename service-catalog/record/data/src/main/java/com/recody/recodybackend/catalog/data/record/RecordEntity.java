package com.recody.recodybackend.catalog.data.record;

import com.recody.recodybackend.catalog.data.RecordBaseEntity;
import com.recody.recodybackend.catalog.data.content.CatalogContentEntity;
import com.recody.recodybackend.catalog.data.user.CatalogUserEntity;
import com.recody.recodybackend.commonbootutils.data.CustomSequenceIdGenerator;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity(name = "Record")
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "record", uniqueConstraints = {
        @UniqueConstraint( name = "user_and_content_id_and_nth_should_be_unique", columnNames = {"content_id", "user_id", "appreciation_number"})
})
@Where(clause = "deleted_at IS null")
@SQLDelete(sql = "UPDATE record SET deleted_at = NOW() WHERE record_id=?") // repository 사용하는 경우, 등
public class RecordEntity extends RecordBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "record_seq")
    @GenericGenerator(
            name = "record_seq",
            strategy = "com.recody.recodybackend.commonbootutils.data.CustomSequenceIdGenerator",
            parameters = {
                    @Parameter(name = CustomSequenceIdGenerator.INCREMENT_PARAM, value = "50"), // high-low 최적화
                    @Parameter(name = CustomSequenceIdGenerator.VALUE_PREFIX_PARAMETER, value = "rec-"),
                    @Parameter(name = CustomSequenceIdGenerator.NUMBER_FORMAT_PARAMETER, value = "%d") })
    private String recordId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "content_id", nullable = false, updatable = false)
    @NonNull
    private CatalogContentEntity content;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    private CatalogUserEntity user;
    
    @Setter
    private String title;
    
    @Setter
    private String note;
    
    @Setter
    private boolean completed;
    
    @Builder.Default
    @Column(name = "appreciation_number", nullable = false)
    private Integer appreciationNumber = 1;
    
    @Column(name = "appreciation_date")
    private LocalDate appreciationDate;
    
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;
    
    public void delete(){
        this.deletedAt = LocalDateTime.now();
    }
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
        return "[{\"RecordEntity\":{" + "\"recordId\":" + ((recordId != null) ? ("\"" + recordId + "\"") : null) + ", \"content\":" + content + ", \"userId\":" + user + ", \"title\":" + ((title != null) ? ("\"" + title + "\"") : null) + ", \"note\":" + ((note != null) ? ("\"" + note + "\"") : null) + ", \"completed\":" + completed + ", \"nth\":" + appreciationNumber + ", \"appreciationDate\":" + appreciationDate + "}}, " + super.toString() + "]";
    }
}
