package org.example.handanddomain.domain.post.application.exception;

import org.example.handanddomain.common.exception.DomainException;
import org.example.handanddomain.common.exception.ErrorCode;

public class PostOwnershipException extends DomainException {

    public PostOwnershipException() {
        super(ErrorCode.POST_ACCESS_DENIED);
    }

}
