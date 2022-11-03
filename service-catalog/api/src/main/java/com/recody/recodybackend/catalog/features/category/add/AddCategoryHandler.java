package com.recody.recodybackend.catalog.features.category.add;


import com.recody.recodybackend.catalog.CustomCategory;

/**
 * 커스텀 카테고리를 추가한다.
 * <ol>
 *     <li> 카테고리의 개수는 최대 10개 </li>
 *     <ul>
 *         <li> 기본 5개, 커스텀 5개 </li>
 *     </ul>
 *     <li> 카테고리의 등록은 유저별로 이루어진다. </li>
 *     <li> 한 유저는 같은 이름의 카테고리를 추가할 수 없다. </li>
 * </ol>
 * @author motive
 */
public interface AddCategoryHandler {
    
    /**
     * 커스텀 카테고리를 추가한다.
     * @param command 카테고리 추가에 필요한 커맨드
     * @return 커스텀 카테고리
     */
    CustomCategory handle(AddCategory command);
    
}
