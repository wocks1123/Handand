package org.example.handanddomain.domain.auth.application.exception;

import org.example.handanddomain.common.exception.DomainException;
import org.example.handanddomain.common.exception.ErrorCode;

public class LoginFailException extends DomainException {

    public LoginFailException() {
        super(ErrorCode.LOGIN_FAIL);
    }

}
