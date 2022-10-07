package com.recody.recodybackend.record.data.content;

import com.recody.recodybackend.record.data.RecordBaseEntity;
import com.recody.recodybackend.record.data.category.EmbeddableCategory;
import com.recody.recodybackend.record.data.record.RecordEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "record_content")
public class RecordContentEntity extends RecordBaseEntity {
    
    @Id
    private String id;
    
    @Embedded
    @Column(nullable = false)
    private EmbeddableCategory category;
    
    @Column(nullable = false, unique = true)
    private String contentId;
    
    private String imageUrl;
    
    @Column(nullable = false)
    private String  title;
    
    @OneToMany(mappedBy = "content")
    private List<RecordEntity> records = new ArrayList<>();
    
    @Override
    public String toString() {
        return "[{\"RecordContentEntity\":{" + "\"id\":" + ((id != null) ? ("\"" + id + "\"") : null) + ", \"category\":" + ((category != null) ? ("\"" + category + "\"") : null) + ", \"contentId\":" + ((contentId != null) ? ("\"" + contentId + "\"") : null) + ", \"imageUrl\":" + ((imageUrl != null) ? ("\"" + imageUrl + "\"") : null) + ", \"title\":" + ((title != null) ? ("\"" + title + "\"") : null) + "}}, " + super.toString() + "]";
    }
}
