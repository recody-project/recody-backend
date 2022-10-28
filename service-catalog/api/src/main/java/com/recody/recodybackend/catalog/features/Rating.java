package com.recody.recodybackend.catalog.features;

/**
 * 별점을 의미합니다.
 * <ul>
 *     <li> 별점이 없는 경우 : value 는 0 입니다. </li>
 *     <li> 별점이 있는 경우 : value 는 1과 10 사이의 정수입니다. </li>
 * </ul>
 * @see UnRated
 * @see RatingScore
 * @author motive
 */
public interface Rating {
    
    int getValue();
    
}
