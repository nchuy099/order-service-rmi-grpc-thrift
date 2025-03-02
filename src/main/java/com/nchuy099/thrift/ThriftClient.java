package com.nchuy099.thrift;

import com.nchuy099.thrift.genJava.OrderRequest;
import com.nchuy099.thrift.genJava.OrderResponse;
import com.nchuy099.thrift.genJava.OrderService;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;
import py4j.GatewayServer;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;

public class ThriftClient {
    public Map<String, Object> request(String productId, int quantity) throws TException {
        TTransport transport = new TSocket("192.168.67.50", 9000);
        if (!transport.isOpen()) transport.open();

        Map<String, Object> resp = new LinkedHashMap<>();
        double res = -1;

        TProtocol protocol = new TBinaryProtocol(transport);
        OrderService.Client client = new OrderService.Client(protocol);

        OrderRequest request = new OrderRequest();
        request.setProductId(productId);
        request.setQuantity(quantity);

        OrderResponse response;
        long start = Instant.now().toEpochMilli();
        response = client.calculateTotal(request);
        long end = Instant.now().toEpochMilli();

        res = response.getResult();

        resp.put("status", res >= 0 ? 1 : 0);
        resp.put("result", res >= 0 ? res : null);
        resp.put("start_time", start);
        resp.put("end_time", end);

        return resp;
    }

    public void hello() {
        System.out.println(1);
    }

    public static void main(String[] args) throws TException {

        ThriftClient thriftClient = new ThriftClient();

        GatewayServer gatewayServer = new GatewayServer(thriftClient);
        gatewayServer.start();
        System.out.println("Py4J Gateway server is running...");    }
}
