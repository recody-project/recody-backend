package com.recody.recodybackend.common.json;

import com.fasterxml.jackson.databind.JsonNode;

public interface WhatItIs {
    /*
    * 이 node 의 값은 text 이다. */
    StringGetter whichIsText();
    
    /*
    * 이 node 의 값은 integer 이다. */
    IntegerGetter whichIsInteger();
    
    /*
    * given node is array, what's next move?
    * array 이고, 이 array 에는 여러 객체가 있다. 만약 문자열이나 숫자 같은 값들이 있다면
    * 아래의 whichIsArrayOf() 를 쓰는 것이 간편하다.  */
    ArrayJsonStream<JsonNode> whichIsArray();
    
    
    /*
    * given node is array, so what type of values does this array have?
    * 이 메서드를 쓰면 해당 array 는 객체를 가지고 있지 않다.
    * 단순 text, number, boolean 등 값들을 가지고 있다. */
    WhatTypeOfValueThisArrayHave whichIsArrayOf();
}
