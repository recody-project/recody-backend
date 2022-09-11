package com.recody.recodybackend.common.json;

public interface WhatTheyAre {
    /*
     * iterating 할 때 방문한 각 노드가 String 타입임을 명시한다. */
    StringCollector whichAreStrings();
    
    /*
     * iterating 할 때 방문한 각 노드가 Integer 타입임을 명시한다. */
    IntegerCollector whichAreIntegers();
    
    /*
    * iterating 할 때 방문한 각 노드가 Array 타입임을 명시한다. */
    WhatTypeOfValueThisArrayHave whichIsArrayOf();
    
    /*
     * iterating 할 때 방문한 각 노드의 타입들을 직접 명시한다. */
    POJOCollector whichTypesAre(Class<?>... classes);
}
