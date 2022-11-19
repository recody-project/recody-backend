package com.recody.recodybackend.catalog.features.category.modify;

import com.recody.recodybackend.category.CategoryIconUrl;
import com.recody.recodybackend.category.CategoryName;
import com.recody.recodybackend.category.CustomCategoryId;
import lombok.*;

/**
 * 커스텀 카테고리를 변경하기 위한 정보.
 * <ul>
 *     <li> 카테고리 Id 는 필수 </li>
 *     <li> 카테고리 이름도 필수 </li>
 *     <li> 카테고리 아이콘 url 은 보내지 않으면 기본값으로 세팅. </li>
 * </ul>
 * @author motive
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ModifyCategory {
    
    private CustomCategoryId categoryId;
    private Long userId;
    private CategoryName categoryName;
    private CategoryIconUrl categoryIconUrl;
    
    @Override
    public String toString() {
        return "{\"ModifyCategory\":{"
               + "\"categoryId\":" + categoryId
               + ", \"userId\":" + userId
               + ", \"categoryName\":" + categoryName
               + ", \"categoryIconUrl\":" + categoryIconUrl
               + "}}";
    }
}
