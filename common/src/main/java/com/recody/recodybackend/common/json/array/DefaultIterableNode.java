package com.recody.recodybackend.common.json.array;

import com.fasterxml.jackson.databind.JsonNode;
import com.recody.recodybackend.common.json.IterableNode;
import com.recody.recodybackend.common.json.JsonHolder;
import com.recody.recodybackend.common.json.WhatTheyAre;
import com.recody.recodybackend.common.json.typechecker.DefaultArrayTypeChecker;

public class DefaultIterableNode extends JsonHolder implements IterableNode {
    
    protected DefaultIterableNode(JsonNode jsonNode) {
        super(jsonNode);
    }
    
    @Override
    public WhatTheyAre whereNameIs(String name) {
        log.debug("iterating array and finding node with name: " + name);
        return new DefaultArrayTypeChecker(jsonNode, name);
    }
    
    /*
    * 이 배열에 name 들이 있는지 체크 */
    @Override
    public WhatTheyAre whereNamesAre(String... names) {
        return new DefaultArrayTypeChecker(jsonNode, names);
    }
    
    /*
    * 이 배열에 값들이 있는지 체크해야 한다. 만약 단순 값이 아니면 예외를 던지자. */
    @Override
    public WhatTheyAre eachValue() {
        return new DefaultArrayTypeChecker(jsonNode);
    }
}
