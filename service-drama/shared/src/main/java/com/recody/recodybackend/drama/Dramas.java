package com.recody.recodybackend.drama;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Dramas {
    
    private List<Drama> dramas;
    
    private Dramas(List<Drama> dramas) {
        this.dramas = dramas;
    }
    
    public static Dramas of(List<Drama> dramas) {
        return new Dramas( dramas );
    }
}
