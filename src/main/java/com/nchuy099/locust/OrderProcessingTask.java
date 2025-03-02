package com.nchuy099.locust;

import com.github.myzhan.locust4j.AbstractTask;
import com.github.myzhan.locust4j.Locust;
import com.nchuy099.RMI.RMIClient;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class OrderProcessingTask extends AbstractTask {

    private final RMIClient rmiClient;

    {
        try {
            rmiClient = new RMIClient();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        } catch (NotBoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getWeight() {
        return 0;
    }

    @Override
    public String getName() {
        return "OrderProcessingTask";
    }

    @Override
    public void execute() throws Exception {
        String productId = String.valueOf(ThreadLocalRandom
                .current().nextInt(1, 1001));
        int quantity = ThreadLocalRandom
                .current().nextInt(1, 101);

        long startTime = Instant.now().toEpochMilli();
        double total = new RMIClient().getTotalCost(productId, quantity);
        System.out.println("Product ID: " + productId + ", quantity: " + quantity + ". Total Cost: " + total);
        long duration = Instant.now().toEpochMilli() - startTime;
        if (total > 0) {
            Locust.getInstance().recordSuccess("custom", "processOrder", duration, 0);
        } else {
            System.out.println("Processing Failed!");
            Locust.getInstance().recordFailure("custom", "processOrder", duration,  "Processing Failed");
        }

        // wait time between requests of a user
        try {
            int waitTime = ThreadLocalRandom.current().nextInt(3000, 5001);
            Thread.sleep(waitTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
