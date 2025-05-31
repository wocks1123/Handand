package org.example.handanddomain.domain.post.application.port.in;

import org.example.handanddomain.domain.post.application.port.in.dto.ModifyPostCommand;
import org.jetbrains.annotations.NotNull;

public interface ModifyPostUseCase {

    @NotNull void modifyPost(@NotNull ModifyPostCommand command);

}
