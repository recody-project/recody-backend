package com.recody.recodybackend.users.features.getuserinfo;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GetUserInfo {
    
    private Long userId;
    
    @Override
    public String toString() {
        return "{\"GetUserInfo\":{"
               + "\"userId\":" + userId
               + "}}";
    }
}
