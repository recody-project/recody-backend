package com.recody.recodybackend.drama;

import com.recody.recodybackend.common.exceptions.ApplicationExceptions;
import lombok.Getter;
import lombok.Value;

@Value(staticConstructor = "of")
@Getter
public class PersonId {
    
    Long value;
    
    public PersonId(Long value) {
        ApplicationExceptions.requireNonNull( value );
        this.value = value;
    }
    
    @Override
    public String toString() {
        return "{\"PersonId\":{"
               + "\"value\":" + value
               + "}}";
    }
}
