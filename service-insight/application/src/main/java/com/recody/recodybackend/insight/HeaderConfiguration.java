package com.recody.recodybackend.insight;

import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

//@Component // bean 이 아니어도 값 주입이 된다?
public class HeaderConfiguration {
    
    @Value( "${insight.record.access-token}" )
    private String accessToken;
    
    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> requestTemplate.header( "Authorization", "Bearer " + accessToken  );
    }
}
