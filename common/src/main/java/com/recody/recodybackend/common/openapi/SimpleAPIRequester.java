package com.recody.recodybackend.common.openapi;

import com.recody.recodybackend.common.openapi.annotation.AuthenticateWith;

@AuthenticateWith(type = AuthType.BASIC_AUTH)
public class SimpleAPIRequester extends AbstractAPIRequester<SimpleAPIRequest>{
    public SimpleAPIRequester() {
        super(AuthType.NO_AUTH);
    }
    
    
    public SimpleAPIRequester(String... authArgs) {
        super(authArgs[0], authArgs[1]);
    }
}
