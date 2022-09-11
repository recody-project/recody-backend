package com.recody.recodybackend.common.json.collectors;

import com.fasterxml.jackson.databind.JsonNode;
import com.recody.recodybackend.common.json.JsonHolder;
import com.recody.recodybackend.common.json.POJOCollector;
import com.recody.recodybackend.common.json.typechecker.ValueType;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings(value = "unchecked")
public class DefaultPOJOCollector extends JsonHolder implements POJOCollector {
    
    private final ValueType[] valueTypes;
    private final String[] names;
    
    public DefaultPOJOCollector(JsonNode jsonNode, ValueType[] valueTypes, String[] names) {
        super(jsonNode);
        this.valueTypes = valueTypes;
        this.names = names;
    }
    @Override
    public <T> List<T> collectInto(Class<T> clazz) {
        log.debug("clazz: "+ clazz);
        log.debug("handling jsonNode: "+ jsonNode);
        ArrayList<Object> objects = new ArrayList<>();
    
        for (int i = 0; i < jsonNode.size(); i++) {
            log.debug("for i = " + i + ",");
            Object[] values = new Object[names.length];
            JsonNode jsonNode = this.jsonNode.get(i);
            log.debug("json value is: " + jsonNode);
            
            for (int j = 0; j < names.length; j++) {
                log.debug("for j = " + j + ",");

                if (this.valueTypes[j] == ValueType.NUMBER) {
                    log.debug("it's number");
                    int integer = jsonNode.get(names[j]).intValue();
                    values[j] = integer;
                    log.debug("its value is " + integer);
    
                } else if (this.valueTypes[j] == ValueType.STRING) {
                    log.debug("it's String");
                    String str = jsonNode.get(names[j]).asText();
                    values[j] = str;
                    log.debug("its value is " + str);
                }
            }
            T obj;
            Class<?>[] types = new Class[valueTypes.length];
            for (int k = 0; k < valueTypes.length; k++) {
                types[k] = valueTypes[k].getClassType();
            }
            try {
                log.debug("trying to construct");
                obj = clazz.getDeclaredConstructor(types).newInstance(values);
                objects.add(obj);
                log.debug("added obj to returning list: " + obj);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
        
        return (List<T>) objects;
    }
}
