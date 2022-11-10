package com.recody.recodybackend.rating;

import com.recody.recodybackend.exceptions.InvalidRatingScoreException;
import com.recody.recodybackend.common.exceptions.ApplicationExceptions;
import lombok.Builder;

import java.util.Objects;

import static com.recody.recodybackend.exceptions.CatalogErrorType.RatingScoreCannotBeNull;

@Builder
public class RatingScore implements Rating {
    
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
    
    public static RatingScore of(Integer value) {
        requireNonNull( value );
        requireValueInBound( value );
        return new RatingScore(value);
    }
    
    private static void requireNonNull(Integer value) {
        if ( Objects.isNull( value )){
            throw ApplicationExceptions.badRequestOf( RatingScoreCannotBeNull );
        }
    }
    
    private static void requireValueInBound(int value) {
        if (value < MIN || value > MAX) {
            throw new InvalidRatingScoreException();
        }
    }
    
    public int getValue() {
        return value;
    }
}
