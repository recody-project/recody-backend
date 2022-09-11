package com.recody.recodybackend.common.openapi;

import com.fasterxml.jackson.databind.JsonNode;
import com.recody.recodybackend.common.json.JsonVisitor;
import com.recody.recodybackend.common.json.JsonVisitorStream;

import java.util.stream.Stream;

public class JsonAPIResponse implements APIResponse<JsonNode> {
    
    private final JsonNode jsonNode;
    
    public JsonAPIResponse(JsonNode jsonNode) { this.jsonNode = jsonNode; }
    
    public static JsonVisitor visitorStream(JsonNode jsonNode) { return new JsonVisitorStream(jsonNode); }
    
    public static JsonAPIResponse of(JsonNode response) { return new JsonAPIResponse(response); }
    
    public Stream<JsonNode> rawStream() { return Stream.of(jsonNode); }
    
    public JsonVisitor visitorStream() { return new JsonVisitorStream(jsonNode); }
    
    @Override
    public JsonNode getResponse() { return jsonNode; }
}
