package com.recody.recodybackend.common.json.typechecker;

import com.fasterxml.jackson.databind.JsonNode;
import com.recody.recodybackend.common.json.*;
import com.recody.recodybackend.common.json.array.DefaultArrayJsonStream;
import com.recody.recodybackend.common.json.collectors.DefaultIntegerCollector;
import com.recody.recodybackend.common.json.collectors.DefaultStringCollector;

public class DefaultArrayTypeChecker extends JsonHolder implements WhatTheyAre {
    private final String name;
    
    public DefaultArrayTypeChecker(JsonNode jsonNode, String name) {
        super(jsonNode);
        this.name = name;
        log.info("name: " + name);
    }
    
    public DefaultArrayTypeChecker(JsonNode jsonNode) {
        super(jsonNode);
        this.name = null;
        log.info("name: " + null);
    }
    
    @Override
    public StringCollector theyAreStrings() {
        if (!jsonNode.get(0).get(name).isTextual()) {
            log.info("jsonNode.get(0): " + jsonNode.get(0));
            throw new IllegalStateException("text 가 아닙니다.");
        }
        log.info("jsonNode(0): "+ jsonNode.get(0));
    
        return new DefaultStringCollector(jsonNode, name);
    }
    
    @Override
    public IntegerCollector theyAreIntegers() {
        if (!jsonNode.get(0).get(name).isInt()) {
            log.info("jsonNode.get(0): " + jsonNode.get(0));
            throw new IllegalStateException("Integer 가 아닙니다.");
        }
        log.info("jsonNode(0): "+ jsonNode.get(0));
    
        return new DefaultIntegerCollector(jsonNode, name);
    }
    
    @Override
    public ArrayJsonStream<JsonNode> theyAreArraysOf() {
        if (!jsonNode.get(0).get(name).isArray()){
            log.info("jsonNode(0): "+ jsonNode.get(0));
            throw new IllegalStateException("Array 가 아닙니다.");
        }
        log.info("they are arrays, look one: "+ jsonNode.get(0));
        return new DefaultArrayJsonStream(jsonNode);
    }
}
