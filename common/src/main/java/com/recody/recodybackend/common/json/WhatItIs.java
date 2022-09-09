package com.recody.recodybackend.common.json;

import com.fasterxml.jackson.databind.JsonNode;

public interface WhatItIs {
    StringGetter itIsText();
    IntegerGetter itIsInteger();
    ArrayJsonStream<JsonNode> itIsArray();
}
