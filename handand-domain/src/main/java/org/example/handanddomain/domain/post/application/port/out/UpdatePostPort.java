package org.example.handanddomain.domain.post.application.port.out;

import org.example.handanddomain.domain.post.domain.Post;
import org.jetbrains.annotations.NotNull;

public interface UpdatePostPort {

    void update(@NotNull Post post);

}
