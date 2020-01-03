package com.github.tonyking97.lucenegrpc.grpc.client;

import com.proto.lucenegrpc.LuceneServiceGrpc;
import com.proto.lucenegrpc.PingRequest;
import com.proto.lucenegrpc.PingResponse;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class LuceneClient {
    public static void main(String[] args) {
        System.out.println("Starting Lucene gRPC Client.");
        System.out.println("Creating Stub.");
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50051)
                .usePlaintext()
                .build();
        LuceneServiceGrpc.LuceneServiceBlockingStub client = LuceneServiceGrpc.newBlockingStub(channel);
        PingRequest request = PingRequest.newBuilder()
                .setPing("ping")
                .build();
        PingResponse response = client.ping(request);
        System.out.println(response.getPong());
    }
}
