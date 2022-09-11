package com.recody.recodybackend.common.json;

import com.fasterxml.jackson.databind.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public abstract class JsonHolder {
    
    protected final JsonNode jsonNode;
    protected final Logger log = LoggerFactory.getLogger(getClass());
    
    protected JsonHolder(JsonNode jsonNode) {
        this.jsonNode = jsonNode;
//        System.out.println("log.isDebugEnabled() = " + log.isDebugEnabled());
    }
}
