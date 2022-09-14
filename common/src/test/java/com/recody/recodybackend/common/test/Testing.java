package com.recody.recodybackend.common.test;

import java.util.HashMap;
import java.util.Map;

class Testing {
    
    public static void main(String[] args) {
        forEach();
    }
    
    private static void forEach() {
        Map<String, String> map = new HashMap<>();
        map.put("Paris", "France");
        map.put("Seoul", "Korea");
        map.put("Tokyo", "Japan");
        map.put("Washington", "USA");
        
        map.entrySet()
                .parallelStream()
           .forEach(entry -> System.out.println("thread: " + Thread.currentThread().getName() + ",\n key: " + entry.getKey() + ", value: " +entry.getValue()));
        
    }
    
    
}
