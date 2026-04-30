package com.example.simple_grpc_post_2604.adapter.in.grpc;

import com.example.simple_grpc_post_2604.domain.Post;
import com.example.simple_grpc_post_2604.usecase.PublishPostUseCase;
import com.example.simple_grpc_post_2604.usecase.ReadPostListUseCase;
import com.example.simple_grpc_post_2604.usecase.ReadPostOneUseCase;
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
//    private final EditPostUseCase editPostUseCase;
//    private final DeletePostUseCase deletePostUseCase;

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

        log.info("[PostGrpcService/readPostOne] request id: {}", request.getId());
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
//
//    @Override
//    public void editPost(PostEditRequest request, StreamObserver<PostEditResponse> responseObserver) {
//        super.editPost(request, responseObserver);
//    }
//
//    @Override
//    public void deletePost(PostDeleteRequest request, StreamObserver<PostDeleteResponse> responseObserver) {
//        super.deletePost(request, responseObserver);
//    }
}
