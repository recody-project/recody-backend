package com.recody.recodybackend.common.json.typechecker;

import com.fasterxml.jackson.databind.node.JsonNodeType;

public enum ValueType {
//                BINARY,
//                BOOLEAN,
//                MISSING,
//                NULL,
                NUMBER(Integer.class, JsonNodeType.NUMBER),
//                OBJECT,
//                POJO,
                STRING(String.class, JsonNodeType.STRING);
    
    private final Class<?> classType;
    private final JsonNodeType jacksonType;
    
    ValueType(Class<?> classType, JsonNodeType jacksonType) {
        this.classType = classType;
        this.jacksonType = jacksonType;
    }
    
    public static ValueType jacksonTypeOf(JsonNodeType jacksonType){
        ValueType[] values = values();
        for (ValueType value : values) {
            if (value.getJacksonType() == jacksonType){
                return value;
            }
        }
        return null;
    }
    
    public static ValueType classTypeOf(Class<?> classType){
        ValueType[] values = values();
        for (ValueType value : values) {
            if (value.getClassType() == classType){
                return value;
            }
        }
        return null;
    }
    
    public JsonNodeType getJacksonType() {
        return jacksonType;
    }
    
    public Class<?> getClassType() {
        return classType;
    }
}
