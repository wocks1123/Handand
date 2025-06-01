package org.example.handanddomain.domain.post.application.port.in;

import org.example.handanddomain.domain.post.application.exception.PostNotFoundException;
import org.example.handanddomain.domain.post.application.exception.PostOwnershipException;
import org.example.handanddomain.domain.post.application.port.in.dto.RemovePostCommand;
import org.jetbrains.annotations.NotNull;

public interface RemovePostUseCase {

    void removePost(@NotNull RemovePostCommand command) throws PostNotFoundException, PostOwnershipException;

}
