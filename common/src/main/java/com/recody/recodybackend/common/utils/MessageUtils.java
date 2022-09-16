package com.recody.recodybackend.common.utils;

import java.util.Arrays;
import java.util.stream.Collectors;

public class MessageUtils {
    
    private static final int MessageSplitMaxSize = 4;
    
    public static String seeDetails(String message) {
        try {
            return Arrays
                    .stream(message.split(" "))
                    .limit(MessageSplitMaxSize).collect(Collectors.joining(" "))
                    .concat(" ...see details");
        } catch (Exception exception) {
            return "...see details";
        }
    }
}
