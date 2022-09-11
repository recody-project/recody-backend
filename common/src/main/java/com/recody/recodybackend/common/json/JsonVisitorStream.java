package com.recody.recodybackend.common.json;

import com.fasterxml.jackson.databind.JsonNode;
import com.recody.recodybackend.common.json.array.DefaultArrayJsonStream;
import com.recody.recodybackend.common.json.typechecker.DefaultTypeChecker;

public class JsonVisitorStream extends JsonHolder implements JsonVisitor{
    
    public JsonVisitorStream(JsonNode jsonNode) {
        super(jsonNode);
    }
    
    public static JsonVisitorStream of(JsonNode jsonNode){
        return new JsonVisitorStream(jsonNode);
    }
    
    @Override
    public WhatItIs go(String name) {
        JsonNode node = this.jsonNode.get(name).deepCopy();
        return new DefaultTypeChecker(node);
    }
    
    @Override
    public ArrayJsonStream<JsonNode> goArray(String name) {
        JsonNode maybeArray = this.jsonNode.get(name).deepCopy();
        if (!maybeArray.isArray()) {
            log.debug("maybeArray: " + maybeArray);
            throw new IllegalStateException("array 가 아닙니다.");
        }
        log.debug("maybeArray: " + maybeArray);
        return new DefaultArrayJsonStream(maybeArray);
    }
}
