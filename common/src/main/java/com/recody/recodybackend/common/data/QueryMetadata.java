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
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QueryMetadata {
    
    private Integer size;
    private Integer currentPage;
    private Long totalResults;
    private Integer totalPages;
    
    public QueryMetadata(Integer size, Integer currentPage, Integer totalPages, Long totalResults) {
        this.size = size;
        this.currentPage = currentPage;
        this.totalPages = totalPages;
        this.totalResults = totalResults;
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
            this.totalResults = page.getTotalElements();
        }
        else {
            this.currentPage = page.getNumber();
            this.totalPages = page.getTotalPages();
            this.totalResults = page.getTotalElements();
        }
    }
    
    public static QueryMetadata empty(){
        return new QueryMetadata( 0, 0, 0, 0L);
    }
}
