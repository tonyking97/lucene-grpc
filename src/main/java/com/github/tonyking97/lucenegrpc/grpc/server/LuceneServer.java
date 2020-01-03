package com.github.tonyking97.lucenegrpc.grpc.server;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class LuceneServer {
    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println("Starting Lucene gRPC Service.");
        Server server = ServerBuilder.forPort(50051)
                .addService(new LuceneServiceImpl())
                .build();

        server.start();
        System.out.println("Lucene gRPC Service Started Successfully");

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Received Shut Down Request.");
            server.shutdown();
            System.out.println("Successfully stopped the Lucene gRPC Server");
        }));

        server.awaitTermination();
    }
}
