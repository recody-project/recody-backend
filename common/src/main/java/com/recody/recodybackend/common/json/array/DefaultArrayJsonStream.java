package com.recody.recodybackend.common.json.array;

import com.fasterxml.jackson.databind.JsonNode;
import com.recody.recodybackend.common.json.ArrayJsonStream;
import com.recody.recodybackend.common.json.IntegerCollector;
import com.recody.recodybackend.common.json.IterableNode;
import com.recody.recodybackend.common.json.JsonHolder;
import com.recody.recodybackend.common.json.collectors.DefaultIntegerCollector;

public class DefaultArrayJsonStream extends JsonHolder implements ArrayJsonStream<JsonNode> {
    
    private String[] names;
    
    public DefaultArrayJsonStream(JsonNode jsonNode) {
        super(jsonNode);
        if (!jsonNode.isArray()) {
            log.info("jsonNode: {}" + jsonNode);
            throw new IllegalStateException("array 가 아닙니다.");
        }
    }
    
    @Override
    public IntegerCollector integers() {
        return new DefaultIntegerCollector(jsonNode, null);
    }
    
    @Override
    public IterableNode iterate() {
        log.info("iterating: {}" + jsonNode);
        return new DefaultIterableNode(jsonNode);
    }
}
