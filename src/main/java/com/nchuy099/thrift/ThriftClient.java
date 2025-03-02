package com.nchuy099.thrift;

import com.nchuy099.thrift.genJava.OrderRequest;
import com.nchuy099.thrift.genJava.OrderResponse;
import com.nchuy099.thrift.genJava.OrderService;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

public class ThriftClient {

    public double getTotalCost(String productId, int quantity) throws TException {
        TTransport transport = new TSocket("192.168.67.50", 9000);
        if (!transport.isOpen()) transport.open();

        TProtocol protocol = new TBinaryProtocol(transport);
        OrderService.Client client = new OrderService.Client(protocol);

        OrderRequest request = new OrderRequest();
        request.setProductId(productId);
        request.setQuantity(quantity);

        OrderResponse response;
        response = client.calculateTotal(request);
        return response.getResult();
    }
}
