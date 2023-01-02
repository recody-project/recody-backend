package com.recody.recodybackend.drama.features.fetchdramadetail;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor( access = AccessLevel.PROTECTED )
public class TDMBDramaGenre {
    
    private Integer id;
    private String name;
    
    @Override
    public String toString() {
        return "{\"TDMBDramaGenre\":{"
               + "\"id\":" + id
               + ", \"name\":" + ((name != null) ? ("\"" + name + "\"") : null)
               + "}}";
    }
}
