package com.recody.recodybackend.users.exceptions;

import com.recody.recodybackend.common.exceptions.ApplicationException;
import org.springframework.http.HttpStatus;

public class InternalServerError extends ApplicationException {
    
    public InternalServerError(String message) {
        super(UsersErrorType.InternalServerError,
              HttpStatus.INTERNAL_SERVER_ERROR,
              "정상적으로 처리하지 못했습니다. 다음 메시지를 개발자에게 알려주세요. \n" +
              message);
    }
}
