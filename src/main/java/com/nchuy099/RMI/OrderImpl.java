package com.nchuy099.RMI;

import com.nchuy099.service.ProductService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class OrderImpl extends UnicastRemoteObject implements Order {
    protected OrderImpl() throws RemoteException {
        super();
    }


    public double calculateTotal(String productId, int quantity) {
        double price = ProductService.getPrice(productId);
        return price * quantity;
    }


}
