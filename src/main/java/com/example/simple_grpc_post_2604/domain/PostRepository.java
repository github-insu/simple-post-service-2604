package com.example.simple_grpc_post_2604.domain;

import java.util.List;

public interface PostRepository {
    Post save(Post post);
    Post findPostById(Long userId);
    List<Post> findPostAll();
    Long deletePostById(Long userId);
}
