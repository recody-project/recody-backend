package com.recody.recodybackend.record.web;

import com.recody.recodybackend.common.utils.FileUtils;
import com.recody.recodybackend.record.RecodyRecordApplication;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc
@ActiveProfiles("test")
@ContextConfiguration(classes = RecodyRecordApplication.class)
@SpringBootTest
public class RecordControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    @Value("${record.access-token}")
    private String accessToken;
    @Value("classpath:testdata/addRecordRequest.json")
    Resource request;
    @Value("classpath:testdata/addRecordRequest_badDateFormat.json")
    Resource badDateFormat;
    
    @Test
    void postRecord() throws Exception {
        String content = FileUtils.readResourceToString(request);
        
        mockMvc.perform(post("/api/v1/record")
                                .header("Authorization", "Bearer " + accessToken)
                                
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(content)
                       )
               
               .andExpect(status().isOk())
               .andExpect(content().string(containsString("recordId")))
               .andDo(print())
        ;
    }
    
    @Test
    @DisplayName("날짜 포맷이 잘못되면 400이 뜬다. ")
    void dateFormat() throws Exception {
        // given
        String content = FileUtils.readResourceToString(badDateFormat);
    
        mockMvc.perform(post("/api/v1/record")
                                .header("Authorization", "Bearer " + accessToken)
            
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(content)
                       )
               .andExpect(status().isBadRequest())
               .andDo(print())
        
        ;
        
        // when
        
        // then
    }
}