package com.recody.recodybackend.catalog.data;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

/**
 * 유저와 작품을 묶는 ID
 * Catalog 서비스에서는 유저가 작품을 조회할 때마다 다양한 이벤트가 발생할 수 있다.
 * - 유저가 작품을 선택하고 별점을 남긴다.
 * - 유저가 작품의 제목을 바꾼다.
 * - 유저가 작품의 카테고리를 바꾼다.
 * 단, 이 id 는 각 id 필드가 조인하는 테이블이 있을 경우, 1:1 관계일때에만 사용한다.
 * user, content 의 조합이 여러번 존재하는 경우에는 별개의 id를 생성하여 사용한다.
 * 예를 들어 '장르를 수정' 하는 경우에는 [유저가 작품] 의 장르를 여러개 수정할 수 있다.
 * 이때에도 이 ID 를 사용하면 불필요한 조인테이블을 생성하여 디자인 하여야 한다.
 * */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class LookupId implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private Long user;
    private String content;
    
    @Override
    public boolean equals(Object o) {
        if ( this == o ) return true;
        if ( !(o instanceof LookupId) ) return false;
        LookupId lookupId = (LookupId) o;
        return Objects.equals( getUser(), lookupId.getUser() ) && Objects.equals( getContent(), lookupId.getContent() );
    }
    
    @Override
    public int hashCode() {
        return Objects.hash( getUser(), getContent() );
    }
}
