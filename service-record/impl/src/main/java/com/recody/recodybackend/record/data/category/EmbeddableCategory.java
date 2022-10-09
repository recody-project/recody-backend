package com.recody.recodybackend.record.data.category;

import lombok.*;

import javax.persistence.Embeddable;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Setter
@Getter
public class EmbeddableCategory {
    
    @NonNull
    private String categoryId;
    @NonNull
    private String name;
    
}
