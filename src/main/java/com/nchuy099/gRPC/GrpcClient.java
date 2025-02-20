package com.nchuy099.gRPC;

import gRPC.OrderRequest;
import gRPC.OrderResponse;
import gRPC.OrderServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class GrpcClient {
    public static void main(String[] args) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50051)
                .usePlaintext()
                .build();

        OrderServiceGrpc.OrderServiceBlockingStub stub =
                OrderServiceGrpc.newBlockingStub(channel);

        OrderRequest request = OrderRequest.newBuilder().setProductId("1").setQuantity(3).build();
        OrderResponse response = stub.calculateTotal(request);

        System.out.println("Total cost: " + response.getResult());
    }
}
