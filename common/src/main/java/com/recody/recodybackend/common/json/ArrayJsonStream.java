package com.recody.recodybackend.common.json;

import com.fasterxml.jackson.databind.JsonNode;

public interface ArrayJsonStream<T extends JsonNode> {
    
    /*
//    * array 안의 모든 요소를 Integer 라고 가정하고 List 로 반환한다. */
    IntegerCollector integers();
    
    /*
     * array 의 모든 요소를 바로 String 이라고 가정하고 List 로 반환한다. */
//    List<String> getStrings();
    
    /*
     * array 안의 모든 요소에 대해 consume 한다. */
//    ArrayJsonStream<T> forEach(Consumer<JsonNode> function);
    
    /*
     * array 에서 n 번째 원소로 간다. 또다른 Visitor 상태가 된다. */
//    JsonVisitor go(int nth);
    
    /*
     * array 의 모든 요소에 대해 적용하는 상태로 간다. */
    IterableNode iterate();
}