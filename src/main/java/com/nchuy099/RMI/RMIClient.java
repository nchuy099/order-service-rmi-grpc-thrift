package com.nchuy099.RMI;

import py4j.GatewayServer;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.time.Instant;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

public class RMIClient {
    private final Registry registry = LocateRegistry.getRegistry("192.168.67.50", 1099);
    private final Order stub = (Order) registry.lookup("OrderService");

    public RMIClient() throws RemoteException, NotBoundException {
    }

    public double getTotalCost(String productId, int quantity) throws RemoteException, NotBoundException {
        return stub.calculateTotal(productId, quantity);
    }

    public static void main(String[] args) {
        try {
            RMIClient rmiClient = new RMIClient();

            GatewayServer gatewayServer = new GatewayServer(rmiClient);
            gatewayServer.start();
            System.out.println("Py4J Gateway server is running...");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}