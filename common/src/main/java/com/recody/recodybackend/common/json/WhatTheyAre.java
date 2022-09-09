package com.recody.recodybackend.common.json;

import com.fasterxml.jackson.databind.JsonNode;

public interface WhatTheyAre {
    /*
     * iterating 할 때 방문한 각 노드가 String 타입임을 명시한다. */
    StringCollector theyAreStrings();
    
    /*
     * iterating 할 때 방문한 각 노드가 Integer 타입임을 명시한다. */
    IntegerCollector theyAreIntegers();
    
    /*
    * iterating 할 때 방문한 각 노드가 Array 타입임을 명시한다. */
    ArrayJsonStream<JsonNode> theyAreArraysOf();
}
