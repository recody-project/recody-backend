package com.recody.recodybackend.record.data;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class LookupId implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private Long userId;
    private String content;
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LookupId)) return false;
        LookupId lookupId = (LookupId) o;
        return Objects.equals(getUserId(), lookupId.getUserId()) && Objects.equals(getContent(),
                                                                                   lookupId.getContent());
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(getUserId(), getContent());
    }
}
