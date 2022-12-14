package com.recody.recodybackend.catalog.data.category;

import com.recody.recodybackend.catalog.data.user.CatalogUserEntity;
import com.recody.recodybackend.common.contents.BasicCategory;
import com.recody.recodybackend.commonbootutils.data.CustomSequenceIdGenerator;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Setter
@Getter
@Builder
@Table(name = "catalog_category")
@Where(clause = "deleted_at IS null")
@SQLDelete(sql = "UPDATE category SET deleted_at = NOW() WHERE id=?")
public class CategoryEntity {
    
    @Id
    @GeneratedValue(generator = "category_seq", strategy = GenerationType.SEQUENCE)
    @GenericGenerator(name = "category_seq",
                      strategy = "com.recody.recodybackend.commonbootutils.data.CustomSequenceIdGenerator",
                      parameters = {
            @Parameter(name = CustomSequenceIdGenerator.INCREMENT_PARAM, value = "50"),// 기본 카테고리를 위한 시퀀스를 미리 선점한다.
            @Parameter(name = CustomSequenceIdGenerator.INITIAL_PARAM, value = "11"),
            @Parameter(name = CustomSequenceIdGenerator.VALUE_PREFIX_PARAMETER, value = "cat-"),
            @Parameter(name = CustomSequenceIdGenerator.NUMBER_FORMAT_PARAMETER, value = "%d")})
    private String id;
    
    @Column(name = "name", nullable = false)
    private String name;
    
    @Column(name = "icon_url")
    private String iconUrl;
    
    @ManyToOne
    @JoinColumn(name = "user_id",
                foreignKey = @ForeignKey(name = "category_may_contain_user_id"))
    private CatalogUserEntity user;
    
    private boolean basic;
    
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;
    
    public CategoryEntity(String id, String name) {
        this.id = id;
        this.name = name;
        this.basic = BasicCategory.isBasic(id);
    }
    
    public void delete(){
        this.deletedAt = LocalDateTime.now();
    }
    
    @Override
    public String toString() {
        return "{\"CategoryEntity\":{" + "\"id\":" + ((id != null) ? ("\"" + id + "\"") : null) + ", \"name\":" + ((name != null) ? ("\"" + name + "\"") : null) + ", \"basic\":" + basic + "}}";
    }
}
