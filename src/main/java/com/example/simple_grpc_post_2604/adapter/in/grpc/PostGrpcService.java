package com.example.simple_grpc_post_2604.adapter.in.grpc;

import com.example.simple_grpc_post_2604.domain.Post;
import com.example.simple_grpc_post_2604.domain.status.PostStatus;
import com.example.simple_grpc_post_2604.usecase.DeletePostUseCase;
import com.example.simple_grpc_post_2604.usecase.EditPostUseCase;
import com.example.simple_grpc_post_2604.usecase.PublishPostUseCase;
import com.example.simple_grpc_post_2604.usecase.ReadPostListUseCase;
import com.example.simple_grpc_post_2604.usecase.ReadPostOneUseCase;
import com.example.simplegrpcpost2604.grpc.PostDeleteRequest;
import com.example.simplegrpcpost2604.grpc.PostDeleteResponse;
import com.example.simplegrpcpost2604.grpc.PostEditRequest;
import com.example.simplegrpcpost2604.grpc.PostEditResponse;
import com.example.simplegrpcpost2604.grpc.PostPublishRequest;
import com.example.simplegrpcpost2604.grpc.PostPublishResponse;
import com.example.simplegrpcpost2604.grpc.PostReadListResponse;
import com.example.simplegrpcpost2604.grpc.PostReadOneRequest;
import com.example.simplegrpcpost2604.grpc.PostReadOneResponse;
import com.example.simplegrpcpost2604.grpc.PostServiceGrpc;
import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.grpc.server.service.GrpcService;

import java.util.List;

@GrpcService
@RequiredArgsConstructor
@Slf4j
public class PostGrpcService extends PostServiceGrpc.PostServiceImplBase {

    private final PublishPostUseCase publishPostUseCase;
    private final ReadPostOneUseCase readPostOneUseCase;
    private final ReadPostListUseCase readPostListUseCase;
    private final EditPostUseCase editPostUseCase;
    private final DeletePostUseCase deletePostUseCase;

    @Override
    public void publishPost(PostPublishRequest request, StreamObserver<PostPublishResponse> responseObserver) {

        log.info("[PostGrpcService/publishPost] request title: {}", request.getTitle());
        Post post = Post.create(request.getTitle(), request.getContent());

        Post publishedPost = publishPostUseCase.publish(post);
        log.info("[PostGrpcService/publishPost] publish saved post id: {}", publishedPost.id());
        PostPublishResponse response = PostPublishResponse.newBuilder()
                .setId(publishedPost.id())
                .setTitle(publishedPost.title())
                .setContent(publishedPost.content())
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void readPostOne(PostReadOneRequest request, StreamObserver<PostReadOneResponse> responseObserver) {

        log.info("[PostGrpcService/readPostOne] request post id: {}", request.getId());
        Post postById = readPostOneUseCase.readPostOne(request.getId());
        log.info("[PostGrpcService/readPostOne] find post id: {}", postById.id());
        PostReadOneResponse response = PostReadOneResponse.newBuilder()
                .setId(postById.id())
                .setTitle(postById.title())
                .setContent(postById.content())
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void readPostList(Empty request, StreamObserver<PostReadListResponse> responseObserver) {

        log.info("[PostGrpcService/readPostList] 함수 호출 성공");
        List<Post> postList = readPostListUseCase.readPostList();
        log.info("[PostGrpcService/readPostList] list size: {}", postList.size());
        log.info("[PostGrpcService/readPostList] list[0] title: {}", postList.getFirst().title());

        PostReadListResponse.Builder responseListBuilder = PostReadListResponse.newBuilder();

        for (Post post: postList) {
            responseListBuilder.addPostReadOneResponseList(
                    PostReadOneResponse.newBuilder()
                            .setId(post.id())
                            .setTitle(post.title())
                            .setContent(post.content())
                            .build()
            );
        }

        responseObserver.onNext(responseListBuilder.build());
        responseObserver.onCompleted();
    }

    @Override
    public void editPost(PostEditRequest request, StreamObserver<PostEditResponse> responseObserver) {
        log.info("[PostGrpcService/editPost] request post id: {}", request.getId());
        log.info("[PostGrpcService/editPost] request post title: {}", request.getTitle());
        Post requestPost = Post.restore(
                request.getId(),
                request.getTitle(),
                request.getContent(),
                PostStatus.PUBLISHED
        );

        Post editedPost = editPostUseCase.editPost(requestPost);
        log.info("[PostGrpcService/editPost] updated post id: {}", request.getId());
        log.info("[PostGrpcService/editPost] updated post title: {}", request.getTitle());

        PostEditResponse response = PostEditResponse.newBuilder()
                .setId(editedPost.id())
                .setTitle(editedPost.title())
                .setContent(editedPost.content())
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void deletePost(PostDeleteRequest request, StreamObserver<PostDeleteResponse> responseObserver) {
        log.info("[PostGrpcService/deletePost] request post id: {}", request.getId());
        Long deletedUserIdById = deletePostUseCase.deletePost(request.getId());
        PostDeleteResponse response = PostDeleteResponse.newBuilder()
                .setId(deletedUserIdById)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
