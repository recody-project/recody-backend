package com.recody.recodybackend.common.openapi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AbstractAPIRequestTest {
    
    private String username = "ZTE4NzI0YzEtOWVhMC00MzExLWFmMWYtY2RkZTI3ODNmMWMy";
    private String password = "NWU5OTEyMzMtY2ZlYS00ODUwLThlNTgtYWVmMTFlMWJkNDg2";
    
    private final SimpleAPIRequester requester = new SimpleAPIRequester(username, password);
    private String baseUri = "https://api.napster.com/v2.2/genres";
    
    @Test
    @DisplayName("test01")
    void test01() {
        // given
        SimpleAPIRequest request = new SimpleAPIRequest(baseUri);
        String string = requester.executeToString(request);
        try {
            String string1 = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(string);
            System.out.println(string);
    
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        // when
        
        // then
    }
    
}