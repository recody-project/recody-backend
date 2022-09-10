package com.recody.recodybackend.common.json;

public interface IterableNode {
    
    /*
    * iterating 할 때 방문할 노드의 이름을 지정한다.
    * 이때 노드는 하나의 key - value 의 필드이다. */
    WhatTheyAre whereNameIs(String name);
    
    /**
    * iterating 할 때 방문할 여러개의 노드 이름을 지정한다.
    * 노드는 객체이고, 여러개의 name 들을 갖는다.
    * - 해당 names 가 객체인 경우에만 가져온다.
    * - 그 이외에 배열이거나 값들이 있다면 무시한다.
    *
    * ======the code would be like ===============================
    * jsonObject.stream()
    *           .go("arrayName")
                .whichIsArray()
                .andIterate()
                .whereNamesAre("name1", "name2", "name3") // this
                .whichTypesAre(String.class, String.class, Integer.class)
                ...
     =============================================================
     ========the array is ========================================
    * "arrayName" : [
    *   { "name1": "value1" },      <- this object is included
    *   { "name2": "value2" },      <- this object is included
    *   { "name3": 3333 },          <- this object is included
    *   "value", 123                <- these values are ignored
    *   ]
    * =============================================================
    * */
    WhatTheyAre whereNamesAre(String... names);
    
    /*
    * iterating 할 때 노드들이 값들임을 알린다.
    * WhatTheyAre 에서는 name 값이 없이 초기화 된다. 따라서 WhatTheyAre 에서 호출되는 모든 메서드는
    * name 값이 없다는 가정하에 로직을 처리한다.
    *   예를 들어 theyAreIntegers() 가 호출된다면, 전달받는 Json node 를 index 로만 확인 후 바로 collector 로 넘긴다.
    * Collector 도 name 없이 바로 모든 원소들을 collect 한다. */
    WhatTheyAre eachValue();
}