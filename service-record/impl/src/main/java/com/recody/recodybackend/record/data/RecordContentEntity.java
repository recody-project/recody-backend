package com.recody.recodybackend.record.data;

import com.recody.recodybackend.common.contents.Category;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "record_content")
public class RecordContentEntity extends RecordBaseEntity{
    
    @Id
    private String id;
    
    @Enumerated(value = EnumType.STRING)
    private Category category;
    
    @Column(nullable = false, unique = true)
    private String contentId;
    
    private String imageUrl;
    
    @Column(nullable = false)
    private String  title;
    
    @Override
    public String toString() {
        return "[{\"RecordContentEntity\":{" + "\"id\":" + ((id != null) ? ("\"" + id + "\"") : null) + ", \"category\":" + ((category != null) ? ("\"" + category + "\"") : null) + ", \"contentId\":" + ((contentId != null) ? ("\"" + contentId + "\"") : null) + ", \"imageUrl\":" + ((imageUrl != null) ? ("\"" + imageUrl + "\"") : null) + ", \"title\":" + ((title != null) ? ("\"" + title + "\"") : null) + "}}, " + super.toString() + "]";
    }
}
