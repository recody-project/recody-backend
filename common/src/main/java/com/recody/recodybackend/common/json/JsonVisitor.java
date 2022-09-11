package com.recody.recodybackend.common.json;

import com.fasterxml.jackson.databind.JsonNode;

/*
* Visitor that never goes back */
public interface JsonVisitor {
    /*
     * 간다 */
    WhatItIs go(String name);
    
    /*
     * array 라고 가정하고 간다. */
    ArrayJsonStream<JsonNode> goArray(String name);
}