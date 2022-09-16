package com.recody.recodybackend.users.web;

import com.recody.recodybackend.common.web.UserBoundDataClass;
import lombok.Data;

@Data
@UserBoundDataClass
public class ReissueTokensRequest {
    
    private String refreshToken;
}
