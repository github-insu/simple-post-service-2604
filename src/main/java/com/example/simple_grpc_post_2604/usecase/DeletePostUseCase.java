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
    public Long deletePost(Long userId) {
        log.info("[DeletePostUseCaes/deletePost] request user id: {}", userId);
        Long deletedUserIdById = postRepository.deletePostById(userId);
        log.info("[DeletePostUseCaes/deletePost] deleted user id: {}", deletedUserIdById);

        return deletedUserIdById;
    }

}
