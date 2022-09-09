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
    public StringGetter itIsText() {
        if (!jsonNode.isTextual())
            throw new IllegalStateException("text 가 아닙니다.");
        return new DefaultStringGetter(jsonNode);
    }
    
    @Override
    public IntegerGetter itIsInteger() {
        if (!jsonNode.isInt())
            throw new IllegalStateException("Integer 가 아닙니다.");
        return new DefaultIntegerGetter(jsonNode);
    }
    
    @Override
    public ArrayJsonStream<JsonNode> itIsArray() {
        if (!jsonNode.isArray())
            throw new IllegalStateException("array 가 아닙니다.");
        return new DefaultArrayJsonStream(jsonNode);
    }
}
