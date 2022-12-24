package com.recody.recodybackend.common.data;

import lombok.*;
import org.springframework.data.domain.Page;

/**
 * 쿼리 결과의 페이지 메타 정보.
 * <li> 페이지는 1에서 시작하도록 만든다. </li>
 * @author motive
 */
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QueryMetadata {
    
    private Integer size;
    private Integer currentPage;
    private Integer totalPages;
    
    private QueryMetadata(Integer size, Integer currentPage, Integer totalPages) {
        this.size = size;
        this.currentPage = currentPage;
        this.totalPages = totalPages;
    }
    
    public QueryMetadata(Page<?> page) {
        this( page, true );
    }
    
    /**
     * 쿼리 메타데이터를 Page 객체를 사용해 초기화한다.
     * @param page 페이지 저보가 있는 Page 객체.
     * @param zeroIndexed Page 객체의 페이지 정보가 0 에서 시작하는지 여부.
     */
    public QueryMetadata(Page<?> page, boolean zeroIndexed) {
        this.size = page.getContent().size();
        if (zeroIndexed){
            this.currentPage = page.getNumber() + 1;
            this.totalPages = page.getTotalPages();
        }
        else {
            this.currentPage = page.getNumber();
            this.totalPages = page.getTotalPages();
        }
    }
    
    public static QueryMetadata empty(){
        return new QueryMetadata( 0, 0, 0);
    }
}
