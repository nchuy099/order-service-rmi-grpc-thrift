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

public class ThriftClient {
    public static void main(String[] args) throws TException {
        TTransport transport = new TSocket("localhost", 9090);
        transport.open();

        TProtocol protocol = new TBinaryProtocol(transport);
        OrderService.Client client = new OrderService.Client(protocol);

        OrderRequest request = new OrderRequest();
        request.setProductId("1");
        request.setQuantity(3);

        OrderResponse response = client.calculateTotal(request);
        System.out.println("Total Cost: " + response.getResult());

        transport.close();
    }
}
