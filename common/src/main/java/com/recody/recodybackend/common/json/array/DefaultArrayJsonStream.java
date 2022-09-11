package com.recody.recodybackend.common.json.array;

import com.fasterxml.jackson.databind.JsonNode;
import com.recody.recodybackend.common.json.ArrayJsonStream;
import com.recody.recodybackend.common.json.IterableNode;
import com.recody.recodybackend.common.json.JsonHolder;

public class DefaultArrayJsonStream extends JsonHolder implements ArrayJsonStream<JsonNode> {
    
    private String[] names;
    
    public DefaultArrayJsonStream(JsonNode jsonNode) {
        super(jsonNode);
        if (!jsonNode.isArray()) {
            log.debug("jsonNode: " + jsonNode);
            throw new IllegalStateException("array 가 아닙니다.");
        }
    }
    
    @Override
    public IterableNode andIterate() {
        log.debug("iterating: " + jsonNode);
        return new DefaultIterableNode(jsonNode);
    }
}
