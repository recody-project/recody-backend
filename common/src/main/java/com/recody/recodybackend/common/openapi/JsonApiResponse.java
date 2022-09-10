package com.recody.recodybackend.common.openapi;

import com.fasterxml.jackson.databind.JsonNode;
import com.recody.recodybackend.common.json.JsonVisitor;
import com.recody.recodybackend.common.json.JsonVisitorStream;

import java.util.stream.Stream;

public class JsonApiResponse implements ApiResponse<JsonNode> {
    
    private final JsonNode jsonNode;
    
    public JsonApiResponse(JsonNode jsonNode) { this.jsonNode = jsonNode; }
    
    public static JsonVisitor visitorStream(JsonNode jsonNode) { return new JsonVisitorStream(jsonNode); }
    
    public static JsonApiResponse of(JsonNode response) { return new JsonApiResponse(response); }
    
    public Stream<JsonNode> rawStream() { return Stream.of(jsonNode); }
    
    public JsonVisitor visitorStream() { return new JsonVisitorStream(jsonNode); }
    
    @Override
    public JsonNode getResponse() { return jsonNode; }
}
