package com.recody.recodybackend.users;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CheckDuplicateEmailResponse {
    
    private boolean exists;
    
}
