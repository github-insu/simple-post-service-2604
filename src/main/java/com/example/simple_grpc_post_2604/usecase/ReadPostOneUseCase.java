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
    public Post readPostOne(Long userId) {

        log.info("[ReadPostOneUseCase/readPostOne] request id: {}", userId);
        Post postById = postRepository.findPostById(userId);
        log.info("[ReadPostOneUseCase/readPostOne] postById id: {}", postById.id());

        return postById;
    }
}
