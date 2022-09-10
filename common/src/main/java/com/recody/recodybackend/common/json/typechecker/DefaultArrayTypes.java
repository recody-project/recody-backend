package com.recody.recodybackend.common.json.typechecker;

import com.fasterxml.jackson.databind.JsonNode;
import com.recody.recodybackend.common.json.IntegerCollector;
import com.recody.recodybackend.common.json.JsonHolder;
import com.recody.recodybackend.common.json.StringCollector;
import com.recody.recodybackend.common.json.WhatTypeOfValueThisArrayHave;
import com.recody.recodybackend.common.json.collectors.DefaultIntegerCollector;
import com.recody.recodybackend.common.json.collectors.DefaultStringCollector;

public class DefaultArrayTypes extends JsonHolder implements WhatTypeOfValueThisArrayHave {
    
    public DefaultArrayTypes(JsonNode jsonNode) {
        super(jsonNode);
    }
    
    @Override
    public IntegerCollector integers() {
        return new DefaultIntegerCollector(jsonNode, null);
    }
    
    @Override
    public StringCollector strings() {
        return new DefaultStringCollector(jsonNode, null);
    }
}
