package com.nchuy099.gRPC;

import gRPC.OrderRequest;
import gRPC.OrderResponse;
import gRPC.OrderServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import py4j.GatewayServer;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;

public class GrpcClient {
    private static final ManagedChannel channel = ManagedChannelBuilder
            .forAddress("192.168.67.50", 50051)
            .usePlaintext()
            .build();

    private static final OrderServiceGrpc.OrderServiceBlockingStub stub =
            OrderServiceGrpc.newBlockingStub(channel);

    public Map<String, Object> request(String productId, int quantity) throws RemoteException, NotBoundException {
        Map<String, Object> resp = new LinkedHashMap<>();
        double res = -1;

        OrderRequest request = OrderRequest.newBuilder()
                        .setProductId(productId)
                        .setQuantity(quantity)
                        .build();

        OrderResponse response;
        long start = Instant.now().toEpochMilli();
        response = stub.calculateTotal(request);
        long end = Instant.now().toEpochMilli();

        res = response.getResult();

        resp.put("status", res >= 0 ? 1 : 0);
        resp.put("result", res >= 0 ? res : null);
        resp.put("start_time", start);
        resp.put("end_time", end);

        return resp;
    }

    public static void main(String[] args) {
        GrpcClient grpcClient = new GrpcClient();

        GatewayServer gatewayServer = new GatewayServer(grpcClient);
        gatewayServer.start();
        System.out.println("Py4J Gateway server is running...");
    }
}
