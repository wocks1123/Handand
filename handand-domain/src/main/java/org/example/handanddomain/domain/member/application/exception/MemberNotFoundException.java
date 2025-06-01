package org.example.handanddomain.domain.member.application.exception;

import org.example.handanddomain.common.exception.DomainException;
import org.example.handanddomain.common.exception.ErrorCode;

public class MemberNotFoundException extends DomainException {

    public MemberNotFoundException(Long memberId) {
        super(ErrorCode.MEMBER_NOT_FOUND, "with id: " + memberId);
    }

    public MemberNotFoundException(String name) {
        super(ErrorCode.MEMBER_NOT_FOUND, "with name: " + name);
    }

}
