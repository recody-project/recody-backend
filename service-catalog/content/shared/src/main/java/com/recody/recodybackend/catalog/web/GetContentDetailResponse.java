package com.recody.recodybackend.catalog.web;

import com.recody.recodybackend.content.PersonalizedContentDetail;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 개인화된 작품 정보.
 * */
@AllArgsConstructor
@Data
public class GetContentDetailResponse {
    
    private PersonalizedContentDetail content;

}
