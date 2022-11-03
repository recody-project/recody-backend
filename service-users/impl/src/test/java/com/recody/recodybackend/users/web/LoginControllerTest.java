package com.recody.recodybackend.users.web;

import com.recody.recodybackend.users.RecodyUserApplication;
import com.recody.recodybackend.users.data.RecodyUserRepository;
import com.recody.recodybackend.users.data.RefreshTokenRepository;
import com.recody.recodybackend.users.features.projection.UserEventPublisher;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@ActiveProfiles("test")
@ContextConfiguration(classes = RecodyUserApplication.class)
class LoginControllerTest {
    
    @Autowired
    MockMvc mockMvc;
    
    @MockBean
    LoginController loginController;
    
    @MockBean
    UserEventPublisher userEventPublisher;
    
    @MockBean
    RefreshTokenRepository refreshTokenRepository;
    
    @MockBean
    RecodyUserRepository recodyUserRepository;
    
    @Test
    @DisplayName( "요청 바디에서 필요한 값들을 모두 충족하면 정상 처리된다." )
    void signup() throws Exception {
        
        mockMvc.perform(post("/api/v1/users/signup")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\n" +
                                         "    \"password\": \"ehddrle\",\n" +
                                         "    \"passwordConfirm\": \"ehddrle\",\n"+
                                         "    \"email\":\"donkeeyee@gmail.com\",\n" +
                                         "    \"nickname\": \"동기\"\n" + "}\n" + "\n")
                       )
                .andExpect(status().isOk())
                .andDo(print())
        ;
    }
    
    @Test
    @DisplayName( "요청 바디에서 필요한 값이 없으면 400 에러가 발생한다. " )
    void signupFail() throws Exception {
        
        mockMvc.perform(post("/api/v1/users/signup")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\n" +
                                         "    \"password\": \"ehddrle\",\n" +
                                         "    \"passwordConfirm\": \"ehddrle\",\n"+
                                         "    \"nickname\": \"동기\"\n" + "}\n" + "\n")
                       )
               .andExpect(status().isBadRequest())
               .andDo(print())
        ;
        mockMvc.perform(post("/api/v1/users/signup")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\n" +
                                         "    \"password\": \"ehddrle\",\n" +
                                         "    \"email\":\"donkeeyee@gmail.com\",\n" +
                                         "    \"nickname\": \"동기\"\n" + "}\n" + "\n")
                       )
               .andExpect(status().isBadRequest())
               .andDo(print())
        ;
        mockMvc.perform(post("/api/v1/users/signup")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\n" +
                                         "    \"passwordConfirm\": \"ehddrle\",\n"+
                                         "    \"email\":\"donkeeyee@gmail.com\",\n" +
                                         "    \"nickname\": \"동기\"\n" + "}\n" + "\n")
                       )
               .andExpect(status().isBadRequest())
               .andDo(print())
        ;
    }
}