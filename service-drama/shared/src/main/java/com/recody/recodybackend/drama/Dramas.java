package com.recody.recodybackend.drama;

import com.recody.recodybackend.common.data.QueryMetadata;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Dramas {
    
    private QueryMetadata metadata;
    private List<Drama> dramas;
    
    private Dramas(List<Drama> dramas) {
        this.dramas = dramas;
    }
    
    public Dramas(List<Drama> dramas, QueryMetadata metadata) {
        this.metadata = metadata;
        this.dramas = dramas;
    }
    
    public static Dramas of(List<Drama> dramas) {
        return new Dramas( dramas );
    }
    public static Dramas of(List<Drama> dramas, QueryMetadata queryMetadata) {
        return new Dramas( dramas, queryMetadata );
    }
}
