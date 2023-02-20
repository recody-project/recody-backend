package com.recody.recodybackend;


import com.recody.recodybackend.common.exceptions.ApplicationExceptions;
import com.recody.recodybackend.common.exceptions.GlobalErrorType;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

public class Monthly {
    
    private YearMonth yearMonth;
    
    private static final List<DateTimeFormatter> dateFormats = List.of( DateTimeFormatter.ofPattern( "yyyy-MM" ),
                                                           DateTimeFormatter.ofPattern( "yyyy-M" ),
                                                           DateTimeFormatter.ISO_LOCAL_DATE );
    
    /**
     * 날짜 문자열은 yyyy-MM 형식이어야 한다.
     */
    private Monthly(String yearMonthString) {
        for (DateTimeFormatter dateFormat : dateFormats) {
            try {
                this.yearMonth = YearMonth.parse( yearMonthString, dateFormat );
            } catch ( Exception ignored ) {
            }
        }
        if ( Objects.isNull( this.yearMonth ) ) {
            throw ApplicationExceptions.badRequestOf( GlobalErrorType.IllegalArgument );
        }
    }
    
    
    public static Monthly of(String yearMonthString) {
        return new Monthly( yearMonthString );
    }
    
    /**
     * @return 이번 달의 1일
     */
    public static LocalDate thisMonthDateOfFirstDay() {
        return LocalDate.now().withDayOfMonth( 1 );
    }
    
    /**
     * 년,월 의 최소값은 1970-01 로 정한다.
     *
     * @return Monthly 의 최소값
     */
    public static Monthly min() {
        return Monthly.of(
                DateTimeFormatter.ofPattern( "yyyy-MM" )
                                 .format( LocalDate.EPOCH ) );
    }
    
    public static Monthly thisMonth() {
        return Monthly.of( YearMonth.now().toString() );
    }
    
    public LocalDate getDateOfFirstDay() {
        return LocalDate.from( yearMonth.atDay( 1 ) );
    }
    
    public boolean isThisMonth() {
        LocalDate now = LocalDate.now();
        return yearMonth.getYear() == now.getYear() &&
               yearMonth.getMonth().equals( now.getMonth() );
    }
    
    public String asString() {
        return this.yearMonth.toString();
    }
}
