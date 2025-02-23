package com.nchuy099.RMI;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RMIClient {
    public static void main(String[] args) {
        try {
            String productId = "1";
            int quantity = 3;
            Registry registry = LocateRegistry.getRegistry("192.168.67.50", 1099);
            Order stub = (Order) registry.lookup("OrderService");
            System.out.println("Total cost for " + quantity + " with id " + productId + ": " + stub.calculateTotal(productId, quantity));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}