package com.recody.recodybackend.common.json.collectors;

import com.fasterxml.jackson.databind.JsonNode;
import com.recody.recodybackend.common.json.JsonHolder;
import com.recody.recodybackend.common.json.TypeCollector;
import com.recody.recodybackend.common.json.typechecker.ValueType;

import java.util.List;

public class DefaultTypeCollector extends JsonHolder implements TypeCollector {
    
    private final ValueType[] valueTypes;
    
    public DefaultTypeCollector(JsonNode jsonNode, ValueType[] valueTypes) {
        super(jsonNode);
        this.valueTypes = valueTypes;
    }
    
    public ValueType[] getValueTypes() { return valueTypes; }
    
    @Override
    public <T> List<T> collect() {
        return null;
    }
}
