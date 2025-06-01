package org.example.handanddomain.domain.post.application.port.in;

import org.example.handanddomain.domain.post.application.exception.PostNotFoundException;
import org.example.handanddomain.domain.post.application.port.in.dto.ModifyPostCommand;
import org.jetbrains.annotations.NotNull;

public interface ModifyPostUseCase {

    void modifyPost(@NotNull ModifyPostCommand command) throws PostNotFoundException;

}
