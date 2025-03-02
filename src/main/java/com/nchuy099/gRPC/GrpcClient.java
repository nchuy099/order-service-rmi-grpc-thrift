package com.nchuy099.gRPC;

import gRPC.OrderRequest;
import gRPC.OrderResponse;
import gRPC.OrderServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;


public class GrpcClient {
    private static final ManagedChannel channel = ManagedChannelBuilder
            .forAddress("192.168.67.50", 50051)
            .usePlaintext()
            .build();

    private static final OrderServiceGrpc.OrderServiceBlockingStub stub =
            OrderServiceGrpc.newBlockingStub(channel);

    public double getTotalCost(String productId, int quantity) throws RemoteException, NotBoundException {

        OrderRequest request = OrderRequest.newBuilder()
                        .setProductId(productId)
                        .setQuantity(quantity)
                        .build();

        OrderResponse response = stub.calculateTotal(request);
        return response.getResult();

    }
}
