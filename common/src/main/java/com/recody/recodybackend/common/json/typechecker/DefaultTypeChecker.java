package com.recody.recodybackend.common.json.typechecker;

import com.fasterxml.jackson.databind.JsonNode;
import com.recody.recodybackend.common.json.*;
import com.recody.recodybackend.common.json.array.DefaultArrayJsonStream;
import com.recody.recodybackend.common.json.collectors.DefaultIntegerGetter;
import com.recody.recodybackend.common.json.collectors.DefaultStringGetter;

public class DefaultTypeChecker extends JsonHolder implements WhatItIs {
    
    public DefaultTypeChecker(JsonNode jsonNode) {
        super(jsonNode);
    }
    
    @Override
    public StringGetter whichIsText() {
        if (!jsonNode.isTextual())
            throw new IllegalStateException("text 가 아닙니다.");
        return new DefaultStringGetter(jsonNode);
    }
    
    @Override
    public IntegerGetter whichIsInteger() {
        if (!jsonNode.isInt())
            throw new IllegalStateException("Integer 가 아닙니다.");
        return new DefaultIntegerGetter(jsonNode);
    }
    
    @Override
    public ArrayJsonStream<JsonNode> whichIsArray() {
        if (!jsonNode.isArray())
            throw new IllegalStateException("array 가 아닙니다.");
        return new DefaultArrayJsonStream(jsonNode);
    }
    
    @Override
    public WhatTypeOfValueThisArrayHave whichIsArrayOf() {
        if (!jsonNode.isArray()){
            log.debug("check first node, and it is not array: "+ jsonNode);
            throw new IllegalStateException("Array 가 아닙니다.");
        }
        log.debug("they are arrays, look one: "+ jsonNode);
        return new DefaultArrayTypes(jsonNode);
    }
}
