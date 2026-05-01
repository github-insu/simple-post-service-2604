package com.example.simple_grpc_post_2604.adapter.out.persistence.repository;

import com.example.simple_grpc_post_2604.domain.status.PostStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PostRdbRepository extends JpaRepository<PostEntity, Long> {

    Optional<PostEntity> findByUserIdAndId(Long userId, Long postId);

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("""
            UPDATE PostEntity p
                SET 
                    p.title = COALESCE(:title, p.title),
                    p.content = COALESCE(:content, p.content),
                    p.status = COALESCE(:status, p.status)
                WHERE p.id = :id AND p.userId = :userId
            """)
    int updatePostFields(
            @Param("id") Long id,
            @Param("title") String title,
            @Param("content") String content,
            @Param("status") PostStatus status,
            @Param("userId") Long userId
    );
}
