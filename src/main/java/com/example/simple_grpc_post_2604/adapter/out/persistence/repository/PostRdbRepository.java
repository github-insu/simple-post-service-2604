package com.example.simple_grpc_post_2604.adapter.out.persistence.repository;

import com.example.simple_grpc_post_2604.domain.status.PostStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostRdbRepository extends JpaRepository<PostEntity, Long> {

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("""
            UPDATE PostEntity p
                SET 
                    p.title = COALESCE(:title, p.title),
                    p.content = COALESCE(:content, p.content),
                    p.status = COALESCE(:status, p.status)
                WHERE p.id = :id
            """)
    int updatePostFields(
            @Param("id") Long id,
            @Param("title") String title,
            @Param("content") String content,
            @Param("status") PostStatus status
    );
}
