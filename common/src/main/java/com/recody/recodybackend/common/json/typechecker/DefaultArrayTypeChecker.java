package com.recody.recodybackend.common.json.typechecker;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.recody.recodybackend.common.json.*;
import com.recody.recodybackend.common.json.collectors.DefaultIntegerCollector;
import com.recody.recodybackend.common.json.collectors.DefaultPOJOCollector;
import com.recody.recodybackend.common.json.collectors.DefaultStringCollector;

public class DefaultArrayTypeChecker extends JsonHolder implements WhatTheyAre {
    
    private final String name;
    private final String[] names;
    
    public DefaultArrayTypeChecker(JsonNode jsonNode, String name) {
        super(jsonNode);
        this.name = name;
        this.names = null;
        log.debug("received name: " + name);
    }
    
    public DefaultArrayTypeChecker(JsonNode jsonNode) {
        super(jsonNode);
        this.name = null;
        this.names = null;
        log.debug("name: " + null);
    }
    
    public DefaultArrayTypeChecker(JsonNode jsonNode, String... names) {
        super(jsonNode);
        this.name = null;
        this.names = names;
        log.debug("name: " + null);
    }
    
    @Override
    public StringCollector whichAreStrings() {
        if (name != null) {
            if (!jsonNode.get(0).get(name).isTextual()) {
                log.debug("0th jsonNode is not String: " + jsonNode.get(0).get(name));
                throw new IllegalStateException("text 가 아닙니다.");
            }
        } else {
            if (!jsonNode.get(0).isTextual()) {
                log.debug("0th jsonNode is not String: " + jsonNode.get(0));
                throw new IllegalStateException("text 가 아닙니다.");
            }
        }
        log.debug("0th jsonNode: "+ jsonNode.get(0));
    
        return new DefaultStringCollector(jsonNode, name);
    }
    
    @Override
    public IntegerCollector whichAreIntegers() {
        if (name != null) {
            if (!jsonNode.get(0).get(name).isInt()) {
                log.debug("checked \"" + name + "\" of first node and it is not integer: " + jsonNode.get(0).get(name));
                throw new IllegalStateException("Integer 가 아닙니다.");
            }
            log.debug("checked \"" + name + "\" of first node and it's integer : {}", jsonNode.get(0).get(name));
    
        } else { // no name, so it's array, checking if its integers
            if (!jsonNode.isEmpty()) {
                if (!jsonNode.get(0).isInt()) {
                    log.debug("checked first value of array and it is not integer: " + jsonNode.get(0));
                    throw new IllegalStateException("Integer 가 아닙니다.");
                }
                log.debug("checked first value of array, and it's integer: {}", jsonNode.get(0));
                return new DefaultIntegerCollector(jsonNode, null);
    
            }
            // it's empty.
            log.debug("this array is Empty so the value is null: {}", jsonNode.get(0));
        }
        return new DefaultIntegerCollector(jsonNode, name);
    }
    
    @Override
    public WhatTypeOfValueThisArrayHave whichIsArrayOf() {
        if (!jsonNode.get(0).get(name).isArray()){
            log.debug("0th jsonNode is not array: "+ jsonNode.get(0).get(name));
            throw new IllegalStateException("Array 가 아닙니다.");
        }
        log.debug("they are arrays, look one: "+ jsonNode.get(0).get(name));
        return new DefaultArrayTypes(jsonNode);
    }
    
    @Override
    public POJOCollector whichTypesAre(Class<?>... classes) {
        ValueType[] valueTypes = new ValueType[classes.length];
        for (int i = 0; i < jsonNode.size(); i++) {// 노드의 개수
            JsonNode node = jsonNode.get(i);
            log.debug("" + i + "th node is " + node);
            for (int j = 0; j < classes.length; j++) { // 한 노드에 필드 개수
                JsonNodeType nodeType = node.get(names[j]).getNodeType();
                log.debug("" + j + "th valueType for " + i + "th node is " + nodeType);
                ValueType givenType = ValueType.classTypeOf(classes[j]);
                log.debug("and given type is: " + givenType);
                if (givenType == ValueType.jacksonTypeOf(nodeType)){
                    log.debug("their types are same");
                    valueTypes[j] = givenType;
                }
            }
        }
        return new DefaultPOJOCollector(jsonNode, valueTypes, names);
    }
}
