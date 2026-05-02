package com.example.simple_grpc_post_2604.domain;

import java.util.List;

public interface PostRepository {
    Post save(Post post);
    Post findPostByUserIdAndId(Long userId, Long postId);
    List<Post> findPostAll();
    int editPost(Post post);
    Long deletePostByUserIdAndId(Long userId, Long postId);
}
