package com.recody.recodybackend.common.json;

public interface IterableNode {
    
    /*
    * iterating 할 때 방문할 노드의 이름을 지정한다. */
    WhatTheyAre each(String name);
    
    WhatTheyAre eachValue();
}