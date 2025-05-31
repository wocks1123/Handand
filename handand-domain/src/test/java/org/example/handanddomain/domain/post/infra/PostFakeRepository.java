package org.example.handanddomain.domain.post.infra;

import org.example.handanddomain.domain.post.application.port.out.DeletePostPort;
import org.example.handanddomain.domain.post.application.port.out.LoadPostPort;
import org.example.handanddomain.domain.post.application.port.out.SavePostPort;
import org.example.handanddomain.domain.post.application.port.out.UpdatePostPort;
import org.example.handanddomain.domain.post.domain.Post;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class PostFakeRepository implements SavePostPort, UpdatePostPort, LoadPostPort, DeletePostPort {

    private final Map<Long, Post> posts = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);


    @Override
    public Optional<Post> findById(@NotNull Long postId) {
        return Optional.ofNullable(posts.get(postId));
    }

    @Override
    public @NotNull Post save(@NotNull Post post) {
        Long id = idGenerator.incrementAndGet();
        Post newPost = new Post(
                id,
                post.getMember(),
                post.getTitle(),
                post.getContent(),
                post.getStatus()
        );
        posts.put(id, newPost);
        return newPost;
    }

    @Override
    public void update(@NotNull Post post) {
        if (post.getId() == null || !posts.containsKey(post.getId())) {
            throw new IllegalArgumentException("Post with ID " + post.getId() + " does not exist.");
        }
        posts.put(post.getId(), post);
    }

    @Override
    public void deleteById(@NotNull Long postId) {
        posts.remove(postId);
    }
}
