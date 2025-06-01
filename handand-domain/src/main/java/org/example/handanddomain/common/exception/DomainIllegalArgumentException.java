package org.example.handanddomain.common.exception;

public class DomainIllegalArgumentException extends DomainException {

    public DomainIllegalArgumentException(String message) {
        super(ErrorCode.ILLEGAL_ARGUMENT, ": " + message);
    }

}
