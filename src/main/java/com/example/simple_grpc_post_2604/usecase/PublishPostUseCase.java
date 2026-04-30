package com.example.simple_grpc_post_2604.usecase;

import com.example.simple_grpc_post_2604.domain.Post;
import com.example.simple_grpc_post_2604.domain.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PublishPostUseCase {

    private final PostRepository postRepository;

    @Transactional
    public Post publish(Post post) {
        log.info("[PublishPostUseCase/publish] post title: {}", post.title());

        Post savedPost = postRepository.save(post);
        log.info("[PublishPostUseCase/publish] saved post title: {}", savedPost.title());

        return savedPost;
    }
}
