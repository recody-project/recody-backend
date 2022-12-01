package com.recody.recodybackend.common.data;

import lombok.*;
import org.springframework.data.domain.Page;

@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QueryMetadata {
    
    private Integer size;
    private Integer currentPage;
    private Integer totalPages;
    
    public QueryMetadata(Integer size, Integer currentPage, Integer totalPages) {
        this.size = size;
        this.currentPage = currentPage;
        this.totalPages = totalPages;
    }
    
    public QueryMetadata(Page<?> page) {
        this( page, true );
    }
    
    public QueryMetadata(Page<?> page, boolean zeroIndexed) {
        this.size = page.getContent().size();
        if (zeroIndexed){
            this.currentPage = page.getNumber() + 1;
        }
        else {
            this.currentPage = page.getNumber();
        }
        this.totalPages = page.getTotalPages();
    }
    
    public static QueryMetadata empty(){
        return new QueryMetadata( 1, 1, 1);
    }
}
