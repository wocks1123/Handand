package org.example.handandapi.ui.post;

import lombok.RequiredArgsConstructor;
import org.example.handandapi.ui.post.dto.ModifyPostRequest;
import org.example.handandapi.ui.post.dto.RegisterPostRequest;
import org.example.handandapi.ui.post.dto.RemovePostRequest;
import org.example.handanddomain.domain.post.application.port.in.GetPostUseCase;
import org.example.handanddomain.domain.post.application.port.in.ModifyPostUseCase;
import org.example.handanddomain.domain.post.application.port.in.RegisterPostUseCase;
import org.example.handanddomain.domain.post.application.port.in.RemovePostUseCase;
import org.example.handanddomain.domain.post.application.port.in.dto.ModifyPostCommand;
import org.example.handanddomain.domain.post.application.port.in.dto.RegisterPostCommand;
import org.example.handanddomain.domain.post.application.port.in.dto.RemovePostCommand;
import org.example.handanddomain.domain.post.domain.Post;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
class PostRestController {

    private final RegisterPostUseCase registerPostUseCase;
    private final GetPostUseCase getPostUseCase;
    private final ModifyPostUseCase modifyPostUseCase;
    private final RemovePostUseCase removePostUseCase;

    @PostMapping
    Long registerPost(@RequestBody RegisterPostRequest request) {
        return registerPostUseCase.registerPost(
                new RegisterPostCommand(
                        request.memberId(),
                        request.title(),
                        request.content(),
                        request.status()
                )
        );
    }

    @GetMapping("/{postId}")
    Post getPost(@PathVariable Long postId) {
        return getPostUseCase.getPost(postId);
    }

    @PutMapping("/{postId}")
    void modifyPost(@PathVariable Long postId,
                    @RequestBody ModifyPostRequest request) {
        modifyPostUseCase.modifyPost(
                new ModifyPostCommand(
                        postId,
                        request.memberId(),
                        request.title(),
                        request.content()
                )
        );
    }

    @DeleteMapping("/{postId}")
    void removePost(@PathVariable Long postId,
                    @RequestBody RemovePostRequest request) {
        removePostUseCase.removePost(
                new RemovePostCommand(
                        postId,
                        request.memberId()
                )
        );
    }

}
