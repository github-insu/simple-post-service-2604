package com.example.simple_grpc_post_2604.domain;

import com.example.simple_grpc_post_2604.domain.status.PostStatus;

import java.util.Objects;

public class Post {

    public static final int MAX_TITLE_LENGTH = 50;
    public static final int MAX_CONTENT_LENGTH = 5000;

    private Long id;
    private String title;
    private String content;
    private PostStatus status;
    private Long userId;

    private Post(Long id, String title, String content, PostStatus status, Long userId) {
        validatePost(title, content, userId);
        this.id = id;
        this.title = title;
        this.content = content;
        this.status = status;
        this.userId = userId;
    }

    public static Post create(String title, String content, Long userId) {
        return new Post(null, title, content, PostStatus.PUBLISHED, userId);
    }

    public static Post restore(Long id, String title, String content, PostStatus status, Long userId) {
        Objects.requireNonNull(id);
        return new Post(id, title, content, status, userId);
    }

    public void validatePost(String title, String content, Long userId) {
        validateTitle(title);
        validateContent(content);
        Objects.requireNonNull(userId);
    }

    public void validateTitle(String title) {
        if (title.isBlank() || title.length() > MAX_TITLE_LENGTH) {
            throw new IllegalArgumentException("유효한 제목을 입력해 주세요.");
        }
    }

    public void validateContent(String content) {
        if (content.isBlank() || content.length() > MAX_CONTENT_LENGTH) {
            throw new IllegalArgumentException("유효한 본문을 입력해 주세요.");
        }
    }

    public void changeTitle(String newTitle) {
        validateTitle(newTitle);
        this.title = newTitle;
    }

    public void changeContent(String newContent) {
        validateContent(newContent);
        this.content = newContent;
    }

    public Long id() {
        return this.id;
    }

    public String title() {
        return this.title;
    }

    public String content() {
        return this.content;
    }

    public PostStatus status() {
        return this.status;
    }

    public Long userId() {
        return this.userId;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Post post = (Post) obj;

        return this.id.equals(post.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
