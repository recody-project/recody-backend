package com.recody.recodybackend.insight.data;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Month;
import java.time.Period;
import java.time.YearMonth;

class InsightMonthTest {
    
    @Test
    @DisplayName( "monthTest" )
    void monthTest() {
        // given
        YearMonth august = YearMonth.of( 2023, Month.AUGUST );
        YearMonth december = YearMonth.of( 2023, Month.DECEMBER );
        // when
        YearMonth result = december.minus( Period.ofMonths( august.getMonthValue() ) );
        System.out.println(result);
    
        DateFormat dateTimeInstance = SimpleDateFormat.getDateInstance(1);
        System.out.println( "dateTimeInstance = " + dateTimeInstance );
        // then
    }
}
