package com.recody.recodybackend.common.openapi;

import com.fasterxml.jackson.databind.JsonNode;
import com.recody.recodybackend.common.json.JsonVisitor;
import com.recody.recodybackend.common.json.JsonVisitorStream;

import java.util.stream.Stream;

public class JsonApiResponse implements ApiResponse<JsonNode> {
    
    private final JsonNode response;
    
    public JsonApiResponse(JsonNode response) { this.response = response; }
    
    public static JsonVisitor stream(JsonNode jsonNode) { return new JsonVisitorStream(jsonNode); }
    
    public static JsonApiResponse of(JsonNode response) { return new JsonApiResponse(response); }
    
    public Stream<JsonNode> rawStream() { return Stream.of(response); }
    
    public JsonVisitor stream() { return new JsonVisitorStream(response); }
    
    @Override
    public JsonNode getResponse() { return response; }
}
