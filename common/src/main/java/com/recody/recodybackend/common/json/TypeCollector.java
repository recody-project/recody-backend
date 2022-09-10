package com.recody.recodybackend.common.json;

import java.util.List;

public interface TypeCollector {
    
    /*
    * T 타입의 list 로 값들을 매핑하여 반환한다. */
    <T> List<T> collect();
}
