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
    public WhatTheyAre each(String name) {
        log.info("iterating array with name: " + name);
        return new DefaultArrayTypeChecker(jsonNode, name);
    }
    
    @Override
    public WhatTheyAre eachValue() {
        return new DefaultArrayTypeChecker(jsonNode);
    }
}
