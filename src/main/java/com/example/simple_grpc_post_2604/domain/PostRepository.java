package com.example.simple_grpc_post_2604.domain;

public interface PostRepository {
    Post save(Post post);
    Post findPostById(Long userId);
}
