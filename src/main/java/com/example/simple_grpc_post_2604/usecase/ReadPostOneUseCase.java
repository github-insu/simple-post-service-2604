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
public class ReadPostOneUseCase {

    private final PostRepository postRepository;

    @Transactional(readOnly = true)
    public Post readPostOne(Long userId, Long postId) {

        log.info("[ReadPostOneUseCase/readPostOne] request user id: {}", userId);
        log.info("[ReadPostOneUseCase/readPostOne] request post id: {}", postId);
        Post postById = postRepository.findPostByUserIdAndId(userId, postId);
        log.info("[ReadPostOneUseCase/readPostOne] postById id: {}", postById.id());
        log.info("[ReadPostOneUseCase/readPostOne] postById id: {}", postById.id());

        return postById;
    }
}
