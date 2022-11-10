package com.recody.recodybackend.catalog.web;

import com.recody.recodybackend.catalog.CatalogRecordConfiguration;
import com.recody.recodybackend.catalog.data.category.CategoryEntity;
import com.recody.recodybackend.catalog.data.category.CategoryRepository;
import com.recody.recodybackend.catalog.data.content.CatalogContentEntity;
import com.recody.recodybackend.catalog.data.content.CatalogContentRepository;
import com.recody.recodybackend.catalog.data.rating.RatingEntity;
import com.recody.recodybackend.catalog.data.rating.RatingRepository;
import com.recody.recodybackend.catalog.data.record.RecordRepository;
import com.recody.recodybackend.common.utils.FileUtils;
import org.junit.jupiter.api.BeforeEach;
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
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc
@ActiveProfiles("test")
@ContextConfiguration(classes = CatalogRecordConfiguration.class)
@SpringBootTest
public class RecordControllerTest {
    
    private static final Long USER_ID = 1L;
    @Autowired
    private MockMvc mockMvc;
    @Value("${record.access-token}")
    private String accessToken;
    @Value( "classpath:testdata/addRecordRequest.json" )
    Resource request;
    
    @Value( "classpath:testdata/addRecordRequest2.json" )
    Resource request2;
    @Value( "classpath:testdata/addRecordRequest_badDateFormat.json" )
    Resource badDateFormat;
    
    @Autowired
    CatalogContentRepository contentRepository;
    
    @Autowired
    RecordRepository recordRepository;
    
    @Autowired
    RatingRepository recordRatingRepository;
    
    CatalogContentEntity savedContent;
    CategoryEntity categoryEntity = new CategoryEntity( "con-2222", "sampleName");
    @Autowired
    CategoryRepository categoryRepository;
    
    @BeforeEach
    void before() {
        recordRepository.deleteAllInBatch();
        contentRepository.deleteAllInBatch();
        categoryRepository.deleteAllInBatch();
        recordRatingRepository.deleteAllInBatch();
        CategoryEntity saved = categoryRepository.save( categoryEntity );
    
        CatalogContentEntity contentEntity = CatalogContentEntity
                                                    .builder()
                                                    .id("catalogId")
                                                    .contentId("con-1111")
                                                    .category( categoryEntity )
                                                    .build();
        savedContent = contentRepository.save(contentEntity);
    }
    @Test
    void postRecord() throws Exception {
        String content = FileUtils.readResourceToString(request);
        RatingEntity ratingEntity = RatingEntity.builder().score( 1 ).userId( USER_ID ).content( savedContent ).build();
        recordRatingRepository.save(ratingEntity);
        mockMvc.perform(addRecordRequest(content)
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
        String contentRequest = FileUtils.readResourceToString(badDateFormat);
    
        
        // when
    
        // then
        mockMvc.perform(addRecordRequest(contentRequest)
                       )
               .andExpect(status().isBadRequest())
               .andDo(print())
    
        ;
    }
    
    private MockHttpServletRequestBuilder addRecordRequest(String content) {
        return post("/api/v1/record")
                       .header("Authorization", "Bearer " + accessToken)
                       .contentType(MediaType.APPLICATION_JSON)
                       .content(content);
    }

    @Test
    @DisplayName("감상평을 저장할때 없는 작품이면 not found 가 뜬다.")
    void addRecord() throws Exception {
        // given


        // when
        String content = FileUtils.readResourceToString(request2);


        // then
        mockMvc.perform(
                       addRecordRequest(content)
                       )
                .andExpect(status().isNotFound())
                .andDo(print())
        ;
    }
}