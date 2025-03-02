package com.nchuy099.RMI;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;


public class RMIClient {
    private static final Registry registry;

    private static final Order stub;

    static {
        try {
            registry = LocateRegistry.getRegistry("192.168.67.50", 1099);
            stub = (Order) registry.lookup("OrderService");
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        } catch (NotBoundException e) {
            throw new RuntimeException(e);
        }
    }

    public double getTotalCost(String productId, int quantity) throws RemoteException, NotBoundException {
        return stub.calculateTotal(productId, quantity);
    }

}