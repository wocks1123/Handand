package org.example.handandapi;

import lombok.RequiredArgsConstructor;
import org.example.handanddomain.PostService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
public class PostRestController {

    private final PostService postService;

    @GetMapping
    public String save() {
        return postService.save();
    }

}
