package com.example.simple_grpc_post_2604.adapter.out.persistence.repository;

import com.example.simple_grpc_post_2604.domain.Post;
import com.example.simple_grpc_post_2604.domain.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    @Override
    public Post findPostById(Long userId) {
        log.info("[PostRdbAdapter/findPostById] request user id: {}", userId);
        PostEntity postById = postRdbRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다.")
                );
        log.info("[PostRdbAdapter/findPostById] find user id: {}", postById.getId());

        return Post.restore(postById.getId(),
                postById.getTitle(),
                postById.getContent(),
                postById.getStatus()
        );
    }

    @Override
    public List<Post> findPostAll() {
        log.info("[PostRdbAdapter/findPostAll] 함수 호출 성공");
        List<PostEntity> entityList = postRdbRepository.findAll();
        log.info("[PostRdbAdapter/findPostAll] list size: {}", entityList.size());
        log.info("[PostRdbAdapter/findPostAll] list[0] title: {}", entityList.getFirst().getTitle());

        return entityList.stream()
                .map(entity -> Post.restore(
                        entity.getId(),
                        entity.getTitle(),
                        entity.getContent(),
                        entity.getStatus()))
                .toList();
    }

    @Override
    public int editPost(Post post) {
        log.info("[PostRdbAdapter/deletePostById] request post id: {}", post.id());
        return postRdbRepository.updatePostFields(post.id(), post.title(), post.content(), post.status());
    }

    @Override
    public Long deletePostById(Long userId) {
        log.info("[PostRdbAdapter/deletePostById] request user id: {}", userId);

        try {
            postRdbRepository.deleteById(userId);
            return userId;
        } catch(Exception ex) {
            throw new IllegalArgumentException("삭제를 실패했습니다.");
        }
    }
}
