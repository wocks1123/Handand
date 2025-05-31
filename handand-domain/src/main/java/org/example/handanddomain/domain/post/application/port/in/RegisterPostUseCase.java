package org.example.handanddomain.domain.post.application.port.in;

import org.example.handanddomain.domain.post.application.port.in.dto.RegisterPostCommand;
import org.jetbrains.annotations.NotNull;

public interface RegisterPostUseCase {

    @NotNull Long registerPost(@NotNull RegisterPostCommand command);

}
