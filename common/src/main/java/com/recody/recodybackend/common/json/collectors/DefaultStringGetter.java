package com.recody.recodybackend.common.json.collectors;

import com.fasterxml.jackson.databind.JsonNode;
import com.recody.recodybackend.common.json.JsonHolder;
import com.recody.recodybackend.common.json.StringGetter;

public class DefaultStringGetter extends JsonHolder implements StringGetter {
    
    public DefaultStringGetter(JsonNode jsonNode) {
        super(jsonNode);
    }
    
    @Override
    public String get() {
        return jsonNode.asText();
    }
}
