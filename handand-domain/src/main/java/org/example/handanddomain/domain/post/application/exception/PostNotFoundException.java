package org.example.handanddomain.domain.post.application.exception;

import org.example.handanddomain.common.exception.DomainException;
import org.example.handanddomain.common.exception.ErrorCode;

public class PostNotFoundException extends DomainException {

    public PostNotFoundException(Long postId) {
        super(ErrorCode.POST_NOT_FOUND, "with id: " + postId);
    }

}
