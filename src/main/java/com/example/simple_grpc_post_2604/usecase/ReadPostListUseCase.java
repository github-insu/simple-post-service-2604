package com.example.simple_grpc_post_2604.usecase;

import com.example.simple_grpc_post_2604.domain.Post;
import com.example.simple_grpc_post_2604.domain.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReadPostListUseCase {

    private final PostRepository postRepository;

    @Transactional(readOnly = true)
    public List<Post> readPostList() {

        log.info("[ReadPostListUseCase/readPostList] 함수 호출 성공");
        List<Post> postList = postRepository.findPostAll();
        log.info("[ReadPostListUseCase/readPostList] post size: {}", postList.size());
        log.info("[ReadPostListUseCase/readPostList] list[0] title: {}", postList.getFirst().title());

        return postList;
    }
}
