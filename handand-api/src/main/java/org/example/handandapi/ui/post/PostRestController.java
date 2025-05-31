package org.example.handandapi.ui.post;

import lombok.RequiredArgsConstructor;
import org.example.handandapi.ui.post.dto.RegisterPostRequest;
import org.example.handanddomain.domain.post.application.port.in.RegisterPostUseCase;
import org.example.handanddomain.domain.post.application.port.in.dto.RegisterPostCommand;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostRestController {

    private final RegisterPostUseCase registerPostUseCase;

    @PostMapping
    Long registerPost(@RequestBody RegisterPostRequest request) {
        return registerPostUseCase.registerPost(
                new RegisterPostCommand(
                        1L, // TODO Fix
                        request.title(),
                        request.content(),
                        request.status()
                )
        );

    }

}
