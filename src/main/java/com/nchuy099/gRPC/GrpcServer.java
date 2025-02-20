package com.nchuy099.gRPC;

import gRPC.OrderRequest;
import gRPC.OrderResponse;
import gRPC.OrderServiceGrpc;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import com.nchuy099.service.ProductService;

public class GrpcServer {
    public static void main(String[] args) throws Exception {
        Server server = ServerBuilder.forPort(50051)
                .addService(new OrderServiceImpl())
                .build()
                .start();
        System.out.println("gRPC Server is running...");
        server.awaitTermination();
    }
}

class OrderServiceImpl extends OrderServiceGrpc.OrderServiceImplBase {
    @Override
    public void calculateTotal(OrderRequest request, StreamObserver<OrderResponse> responseObserver) {
        double result = ProductService.getPrice(request.getProductId()) * request.getQuantity();
        OrderResponse response = OrderResponse.newBuilder().setResult(result).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
