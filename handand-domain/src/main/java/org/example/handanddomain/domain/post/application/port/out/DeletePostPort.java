package org.example.handanddomain.domain.post.application.port.out;

import org.jetbrains.annotations.NotNull;

public interface DeletePostPort {

    void deleteById(@NotNull Long postId);

}
