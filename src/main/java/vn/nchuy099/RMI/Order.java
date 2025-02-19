package vn.nchuy099.RMI;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Order extends Remote {
    double calculateTotal(String productId, int quantity)  throws RemoteException;
}
