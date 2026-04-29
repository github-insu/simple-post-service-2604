package com.example.simple_grpc_post_2604.adapter.in.grpc;

import com.example.simple_grpc_post_2604.domain.Post;
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

@GrpcService
@RequiredArgsConstructor
@Slf4j
public class PostGrpcService extends PostServiceGrpc.PostServiceImplBase {

    private final PublishPostUseCase publishPostUseCase;
//    private final ReadPostOneUseCase readPostOneUseCase;
//    private final ReadPostListUseCase readPostListUseCase;
//    private final EditPostUseCase editPostUseCase;
//    private final DeletePostUseCase deletePostUseCase;

    @Override
    public void publishPost(PostPublishRequest request, StreamObserver<PostPublishResponse> responseObserver) {

        log.info("[PostGrpcService/publishPost] request title: {}", request.getTitle());
        Post post = Post.create(request.getTitle(), request.getContent());
        PostPublishResponse response = publishPostUseCase.publish(post);

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

//    @Override
//    public void readPostOne(PostReadOneRequest request, StreamObserver<PostReadOneResponse> responseObserver) {
//        super.readPostOne(request, responseObserver);
//    }
//
//    @Override
//    public void readPostList(Empty request, StreamObserver<PostReadListResponse> responseObserver) {
//        super.readPostList(request, responseObserver);
//    }
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
