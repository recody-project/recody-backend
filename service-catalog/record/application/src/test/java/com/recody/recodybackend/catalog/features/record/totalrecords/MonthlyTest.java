package com.recody.recodybackend.catalog.features.record.totalrecords;

import com.recody.recodybackend.Monthly;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class MonthlyTest {
    
    @Test
    @DisplayName( "yearMonthTest" )
    void yearMonthTest() {
        // given
        LocalDate localDate = Monthly.thisMonthDateOfFirstDay();
        // when
        System.out.println(localDate);
        // then
    }
    
    @Test
    @DisplayName( "주어진 객체를 LocalDate 객체로 반환한다." )
    void getMonth() {
        // given
        Monthly monthly = Monthly.of( "2023-05" );
    
        // when
        LocalDate date = monthly.getDateOfFirstDay();
        
        // then
    
        System.out.println(date);
        assertThat( date.getMonthValue() ).isEqualTo( 5 );
        assertThat( date.getYear() ).isEqualTo( 2023 );
        assertThat( date.getDayOfMonth() ).isEqualTo( 1 );
        
    }
}