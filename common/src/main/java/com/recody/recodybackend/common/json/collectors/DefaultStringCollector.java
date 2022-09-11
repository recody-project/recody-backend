package com.recody.recodybackend.common.json.collectors;

import com.fasterxml.jackson.databind.JsonNode;
import com.recody.recodybackend.common.json.JsonHolder;
import com.recody.recodybackend.common.json.StringCollector;

import java.util.ArrayList;
import java.util.List;

public class DefaultStringCollector extends JsonHolder implements StringCollector {
    
    private final String name;
    
    public DefaultStringCollector(JsonNode jsonNode, String name) {
        super(jsonNode);
        this.name = name;
    }
    
    @Override
    public List<String> collect() {
        ArrayList<String> strings = new ArrayList<>();
        for (JsonNode node : jsonNode) {
            strings.add(node.get(name).asText());
        }
        log.debug("collected strings: " + strings);
        return strings;
    }
}
