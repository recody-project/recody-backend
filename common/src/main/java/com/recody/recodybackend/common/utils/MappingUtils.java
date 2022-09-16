package com.recody.recodybackend.common.utils;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.lang.reflect.Field;

public class MappingUtils {
    
    public static MultiValueMap<String, String> toMultiValueMap(Object obj){
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        Field[] declaredFields = obj.getClass().getDeclaredFields();
        for (Field declaredField : declaredFields) {
            String name = declaredField.getName();
            declaredField.setAccessible(true);
            Object value;
            try {
                value = declaredField.get(obj);
            } catch (IllegalAccessException e) {
                throw new RuntimeException("필드를 가져오지 못했습니다.");
            }
            map.add(name, (String) value);
        }
        return map;
    }
}
