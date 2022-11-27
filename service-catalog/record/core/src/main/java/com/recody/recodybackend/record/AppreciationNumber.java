package com.recody.recodybackend.record;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.recody.recodybackend.common.exceptions.ApplicationExceptions;
import com.recody.recodybackend.exceptions.CatalogErrorType;
import lombok.Getter;

import java.util.Objects;

/**
 * 특정 작품을 감상한 회차.
 * <li> 1 이상이어야 한다. </li>
 * <li> null 일 경우 1을 부여한다. </li>
 *
 * @author motive
 */
@Getter
public class AppreciationNumber {
    
    private static final int MINIMUM_APPRECIATION_NUMBER = 1;
    @JsonProperty( "appreciationNumber" )
    private Integer value;
    
    private AppreciationNumber(Integer value) {
        if ( Objects.isNull( value ) ) {
            this.value = 1;
            return;
        }
        requireMinimumValue( value );
        this.value = value;
    }
    
    private static void requireMinimumValue(Integer value) {
        if ( value < MINIMUM_APPRECIATION_NUMBER ) {
            throw ApplicationExceptions.badRequestOf( CatalogErrorType.AppreciationNumberCannotBeNegative );
        }
    }
    
    public static AppreciationNumber of(Integer value) {
        return new AppreciationNumber( value );
    }
    
    
    /**
     * 첫번째 회차를 가리킨다.
     * AppreciationNumber 가 null 이 되는 것을 피하기위해 기본값으로 사용할 수 있다.
     */
    public static AppreciationNumber first() {
        return new AppreciationNumber( 1 );
    }
}
