package com.recody.recodybackend.catalog.features;

import com.recody.recodybackend.catalog.exceptions.InvalidRatingScoreException;

public class RatingScore {
    
    /**
     * 1을 포함하여 가능합니다.
     */
    private static final int MIN = 1;
    /**
     * 10을 포함하여 가능합니다.
     */
    private static final int MAX = 10;
    private final int value;
    
    private RatingScore(int value) {
        this.value = value;
    }
    
    public static RatingScore of(int value) {
        assertThatValueOutOfBound(value);
        return new RatingScore(value);
    }
    
    private static void assertThatValueOutOfBound(int value) {
        if (value < MIN || value > MAX) {
            throw new InvalidRatingScoreException();
        }
    }
    
    public int getValue() {
        return value;
    }
}
