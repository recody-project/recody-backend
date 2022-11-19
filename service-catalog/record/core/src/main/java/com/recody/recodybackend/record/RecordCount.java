package com.recody.recodybackend.record;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.recody.recodybackend.common.exceptions.ApplicationException;
import com.recody.recodybackend.exceptions.RecordErrorType;
import lombok.Value;
import org.springframework.http.HttpStatus;

@Value(staticConstructor = "of")
public class RecordCount {
    
    @JsonProperty(value = "recordCount")
    Integer value;
    
    private RecordCount(Integer value) {
        validateValue( value );
        this.value = value;
    }
    
    private static void validateValue(Integer value) {
        if ( value == null){
            throw new ApplicationException( RecordErrorType.RecordCountCannotBeNull, HttpStatus.INTERNAL_SERVER_ERROR );
        }
        else {
            if ( value < 0 ) {
                throw new ApplicationException( RecordErrorType.RecordCountCannotBeNegative, HttpStatus.INTERNAL_SERVER_ERROR );
            }
        }
    }
    
    @Override
    public String toString() {
        return "{\"RecordCount\":{"
               + "\"value\":" + value
               + "}}";
    }
}
