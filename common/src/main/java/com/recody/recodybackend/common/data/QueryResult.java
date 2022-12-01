package com.recody.recodybackend.common.data;

import lombok.*;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QueryResult<T> {
    
    private QueryMetadata metadata;
    
    private List<T> content;
    
    public QueryResult(Page<?> page, List<T> content) {
        this.metadata = new QueryMetadata( page );
        this.content = content;
    }
    
    private QueryResult(List<T> content) {
        this.metadata = QueryMetadata.empty();
        this.content = content;
    }
    
    public static <S> QueryResult<S> just(List<S> content){
        return new QueryResult<>( content );
    }
}
