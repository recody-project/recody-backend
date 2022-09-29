package com.recody.recodybackend.catalog.data;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

/**
 * 유저와 작품을 묶는 ID
 * Catalog 서비스에서는 유저가 작품을 조회할 때마다 다양한 이벤트가 발생할 수 있다.
 * - 유저가 작품을 선택해야 감상평 작성이 가능함.
 * - 유저가 작품을 선택하고 별점을 남긴다.*/
@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LookupId implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Column(name = "user_id", length = 50)
    private String userId;
    @Column(name = "content_id", length = 50)
    private String contentId;

    public LookupId(String userId, String contentId) {
        this.userId = userId;
        this.contentId = contentId;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LookupId)) return false;
        LookupId lookupId = (LookupId) o;
        return Objects.equals(getUserId(), lookupId.getUserId()) && Objects.equals(getContentId(),
                                                                                   lookupId.getContentId());
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(getUserId(), getContentId());
    }
}
