package com.recody.recodybackend.catalog.data.category;

import com.recody.recodybackend.commonbootutils.data.CustomSequenceIdGenerator;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Setter
@Getter
@Builder
@Table(name = "category")
public class CategoryEntity {
    
    @Id
    @GeneratedValue(generator = "category_seq")
    @GenericGenerator(name = "category_seq",
                      strategy = "com.recody.recodybackend.commonbootutils.data.CustomSequenceIdGenerator",
                      parameters = {
            @Parameter(name = CustomSequenceIdGenerator.INCREMENT_PARAM, value = "50"),
            // 기본 카테고리를 위한 시퀀스를 미리 선점한다.
            @Parameter(name = CustomSequenceIdGenerator.INITIAL_PARAM, value = "11"),
            @Parameter(name = CustomSequenceIdGenerator.VALUE_PREFIX_PARAMETER, value = "cat-"),
            @Parameter(name = CustomSequenceIdGenerator.NUMBER_FORMAT_PARAMETER, value = "%d")})
    private String id;
    
    private String name;
    
    private boolean basic;
    
    @Override
    public String toString() {
        return "{\"CategoryEntity\":{" + "\"id\":" + ((id != null) ? ("\"" + id + "\"") : null) + ", \"name\":" + ((name != null) ? ("\"" + name + "\"") : null) + ", \"basic\":" + basic + "}}";
    }
}
