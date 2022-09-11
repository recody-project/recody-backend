package com.recody.recodybackend.common.json.collectors;

import com.fasterxml.jackson.databind.JsonNode;
import com.recody.recodybackend.common.json.IntegerCollector;
import com.recody.recodybackend.common.json.JsonHolder;

import java.util.ArrayList;
import java.util.List;

public class DefaultIntegerCollector extends JsonHolder implements IntegerCollector {
    
    private final String name;
    
    public DefaultIntegerCollector(JsonNode jsonNode, String name) {
        super(jsonNode);
        this.name = name;
    }
    
    @Override
    public List<Integer> collect() {
        ArrayList<Integer> integers = new ArrayList<>();
        if (name != null) {
            for (JsonNode node : jsonNode) {
                integers.add(node.get(name).asInt());
            }
        } else {
            for (JsonNode node : jsonNode) {
                integers.add(node.asInt());
            }
        }
        return integers;
    }
}
