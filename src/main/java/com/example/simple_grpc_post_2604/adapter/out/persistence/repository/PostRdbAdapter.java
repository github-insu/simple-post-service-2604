package com.example.simple_grpc_post_2604.adapter.out.persistence.repository;

import com.example.simple_grpc_post_2604.domain.Post;
import com.example.simple_grpc_post_2604.domain.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
@Slf4j
public class PostRdbAdapter implements PostRepository {

    private final PostRdbRepository postRdbRepository;

    @Override
    public Post save(Post post) {
        log.info("[PostRdbAdapter/save] post title: {}", post.title());
        PostEntity entity = PostEntity.builder()
                .title(post.title())
                .content(post.content())
                .build();
        PostEntity savedEntity = postRdbRepository.save(entity);

        return Post.restore(
                savedEntity.getId(),
                savedEntity.getTitle(),
                savedEntity.getContent(),
                savedEntity.getStatus()
        );
    }
}
