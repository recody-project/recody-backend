package com.recody.recodybackend.catalog.features.getdetail;

import com.recody.recodybackend.catalog.features.PersonalizedContentDetail;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 개인화된 작품 정보.
 * */
@AllArgsConstructor
@Data
public class GetContentDetailResult {
    
    private PersonalizedContentDetail content;

}
