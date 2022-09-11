package com.recody.recodybackend.common.json.collectors;

import com.fasterxml.jackson.databind.JsonNode;
import com.recody.recodybackend.common.json.IntegerGetter;
import com.recody.recodybackend.common.json.JsonHolder;

public class DefaultIntegerGetter extends JsonHolder implements IntegerGetter {
    
    public DefaultIntegerGetter(JsonNode jsonNode) {
        super(jsonNode);
    }
    
    @Override
    public Integer get() {
        return jsonNode.asInt();
    }
}
