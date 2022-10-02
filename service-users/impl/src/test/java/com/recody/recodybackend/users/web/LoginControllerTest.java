package com.recody.recodybackend.users.web;

import com.recody.recodybackend.users.RecodyUserApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("test")
@ContextConfiguration(classes = RecodyUserApplication.class)
class LoginControllerTest {
    
    @Autowired
    MockMvc mockMvc;
    
    @Value("${record.access-token}")
    private String accessToken;
    @Test
    void signup() throws Exception {
        mockMvc.perform(post("/api/v1/users/signup")
                                .header("Authorization", "Bearer " + accessToken)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\n" +
                                         "    \"username\": \"ehdrdle\",\n" +
                                         "    \"password\": \"ehddrle\",\n" +
                                         "    \"email\":\"donkeeyee@gmail.com\",\n" +
                                         "    \"nickname\": \"동기\"\n" + "}\n" + "\n")
                       )
                .andExpect(status().isOk())
                .andDo(print())
        ;
    }
}