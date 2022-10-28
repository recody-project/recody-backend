package com.recody.recodybackend.common.events;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CategoryDeleted {
    
    @Builder.Default
    private CategoryEventType type = CategoryEventType.CustomCategoryDeleted;
    private String categoryId;
    private LocalDateTime deleteAt;
    
    @Override
    public String toString() {
        return "{\"CategoryDeleted\":{"
               + "\"type\":" + ((type != null) ? ("\"" + type + "\"") : null)
               + ", \"categoryId\":" + ((categoryId != null) ? ("\"" + categoryId + "\"") : null)
               + ", \"deleteAt\":" + ((deleteAt != null) ? ("\"" + deleteAt + "\"") : null)
               + "}}";
    }
}
