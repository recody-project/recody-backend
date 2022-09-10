package com.recody.recodybackend.common.json;

import com.fasterxml.jackson.databind.JsonNode;

public interface ArrayJsonStream<T extends JsonNode> {
    
    /*
     * array 안의 모든 요소에 대해 consume 한다. */
//    ArrayJsonStream<T> forEach(Consumer<JsonNode> function);
    
    /*
     * array 에서 n 번째 원소로 간다. 또다른 Visitor 상태가 된다. */
//    JsonVisitor go(int nth);
    
    /*
     * array 의 모든 요소에 대해 적용하는 상태로 간다.
     * iterate 할 수 있는 경우에는
     *  1. 값인 경우
     *  2. 객체인 경우
     *  3. 필드인 경우 */
    IterableNode andIterate();
    
    /*
    * 여러개의 값을 가져오는 경우 */
    
}