package com.github.tonyking97.lucenegrpc.grpc.server;

import com.proto.lucenegrpc.LuceneServiceGrpc;
import com.proto.lucenegrpc.PingRequest;
import com.proto.lucenegrpc.PingResponse;
import io.grpc.stub.StreamObserver;

public class LuceneServiceImpl extends LuceneServiceGrpc.LuceneServiceImplBase {
    @Override
    public void ping(PingRequest request, StreamObserver<PingResponse> responseObserver) {
        String ping = request.getPing();
        System.out.println(ping);
        PingResponse response = PingResponse.newBuilder()
                .setPong("pong")
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
