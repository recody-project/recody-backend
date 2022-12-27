package com.recody.recodybackend.users.features.allusers;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FetchAllUsers {
    
    private Long userId;
    
    @Override
    public String toString() {
        return "{\"FetchAllUsers\":{"
               + "\"userId\":" + userId
               + "}}";
    }
}
