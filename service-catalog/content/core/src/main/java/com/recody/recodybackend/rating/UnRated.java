package com.recody.recodybackend.rating;

import lombok.*;

/**
 * 작품에 남긴 별점이 없음을 알리는 클래스 입니다.
 * @author motive
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UnRated implements Rating {
    
    private int value;
    private String description = "unrated";
    
    @Override
    public int getValue() {
        return 0;
    }
}
