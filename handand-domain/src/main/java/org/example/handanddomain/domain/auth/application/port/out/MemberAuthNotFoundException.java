package org.example.handanddomain.domain.auth.application.port.out;

import org.example.handanddomain.common.exception.DomainException;
import org.example.handanddomain.common.exception.ErrorCode;

public class MemberAuthNotFoundException extends DomainException {

    public MemberAuthNotFoundException(ErrorCode errorCode) {
        super(errorCode);

    }
}
