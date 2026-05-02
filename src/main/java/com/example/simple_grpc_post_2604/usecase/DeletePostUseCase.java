package com.example.simple_grpc_post_2604.usecase;

import com.example.simple_grpc_post_2604.domain.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class DeletePostUseCase {

    private final PostRepository postRepository;

    @Transactional
    public Long deletePost(Long userId, Long postId) {
        log.info("[DeletePostUseCase/deletePost] request user id: {}", userId);
        log.info("[DeletePostUseCase/deletePost] request post id: {}", postId);
        Long deletedPostIdById = postRepository.deletePostByUserIdAndId(userId, postId);
        log.info("[DeletePostUseCase/deletePost] deleted post id: {}", deletedPostIdById);

        return deletedPostIdById;
    }

}
