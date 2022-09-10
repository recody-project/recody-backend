package com.recody.recodybackend.common.json;

public interface WhatTypeOfValueThisArrayHave {
    /*
     * array 의 값들이 모두 integer 이다.
     * integer 인 기본 값들로 이루어져있으므로 name 은 null 이다.*/
    IntegerCollector integers();
    
    StringCollector strings();
    
}
