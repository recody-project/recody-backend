package com.recody.recodybackend.common.json;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.logging.Logger;

public abstract class JsonHolder {
    
    protected final JsonNode jsonNode;
    protected final Logger log = Logger.getLogger(getClass().toGenericString());
    
    protected JsonHolder(JsonNode jsonNode) {
        log.fine("eongeo");
        this.jsonNode = jsonNode;
    }
}
