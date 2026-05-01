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
public class EditPostUseCase {

    private final PostRepository postRepository;

    @Transactional
    public Post editPost(Post post) {
        log.info("[EditPostUseCase/editPost] request post id: {}", post.id());
        int editedRowCount = postRepository.editPost(post);
        log.info("[EditPostUseCase/editPost] edit row count: {}", editedRowCount);

        if (editedRowCount == 0) {
            throw new IllegalArgumentException("수정 작업이 실패했습니다.");
        }

        Post editedPostById = postRepository.findPostByUserIdAndId(post.userId(), post.id());
        log.info("[EditPostUseCase/editPost] find edited post title: {}", editedPostById.title());
        return editedPostById;
    }
}
