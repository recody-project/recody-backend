package com.recody.recodybackend.users.features.signin.applelogin;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AppleLoginRequestBody {
    private String userIdentifier;
}
