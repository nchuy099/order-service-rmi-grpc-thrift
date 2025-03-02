## "Order processing" Service
### Overview
- This proj implements an Order Processing Service using three different RPC frameworks: Java RMI, gRPC, and Apache Thrift.
- The goal is to compare their performance in terms of latency, throughput, resource usage, and ease of implementation.
### Features
- Simulates order processing by calculating total cost from product ID and quantity.
- Implements Java RMI, gRPC, and Apache Thrift for remote communication.
- Uses Locust and Locust4j for performance benchmarking and load testing.
### Technologies Used
- Java (for all implementations)
- gRPC (with Protocol Buffers for serialization)
- Apache Thrift
- Locust & Locust4j (for load testing)
- Ubuntu 20.04 (server environment)
### Snapshots
Server \
<img src="https://github.com/user-attachments/assets/d0877aa1-700e-49b1-8108-4ac9a0831225" width="400">

Client \
<img src="https://github.com/user-attachments/assets/dfa3ae95-f042-4093-8de1-5690d0c53027" width="400">

Locust \
<img src="https://github.com/user-attachments/assets/d25b48a4-6862-4291-8958-eaed2fa68659" width="400">
