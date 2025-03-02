package com.nchuy099.locust;

import com.github.myzhan.locust4j.Locust;

public class LocustWorker {
    public static void main(String[] args) {
        Locust.getInstance().run(new OrderProcessingTask());
        System.out.println("Locust is running...");
    }
}
