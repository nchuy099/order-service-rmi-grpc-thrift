package com.nchuy099.RMI;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RMIServer {
    public static void main(String[] args) {
        try {
            Order order = new OrderImpl();
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.rebind("OrderService", order);
            System.out.println("RMI Server is running...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}