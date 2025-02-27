from locust import User, task, between, events
from py4j.java_gateway import JavaGateway
import random

gateway = JavaGateway()

class TestUser(User):
    wait_time = between(0, 0)
    total_requests = 0
    start_time = None
    end_time = None
    # rmi_client = gateway.entry_point
    # grpc_client = gateway.entry_point
    # thrift_client = gateway.entry_point

    @task 
    def test(self):
        product_id = str(random.randint(1, 1000))
        quantity = random.randint(1,100)
        try:
            
            # resp = rmi_client.request(product_id, quantity)
            
            # # resp = grpc_client.request(product_id, quantity)
            thrift_client = gateway.entry_point
            resp = thrift_client.request(product_id, quantity)
            success = resp["status"]
            result = resp.get("result", None)
            start = resp["start_time"]
            end = resp["end_time"]
            response_time = (end - start)
            
            if success:
                TestUser.total_requests += 1
                if TestUser.start_time is None or start < TestUser.start_time:
                    TestUser.start_time = start
                if TestUser.end_time is None or end > TestUser.end_time:
                    TestUser.end_time = end
        
            events.request.fire(
                # request_type="RMI",
                # request_type="gRPC",
                request_type="thrift",
                name="Calculate Total",
                response_time=response_time,
                response_length=len(str(result)) if success else 0,
                exception=None if success else "Request failed"
            )
            print(f"Product: {product_id}, Quantity: {quantity}, Cost: {result}, Success: {success}, Response Time: {response_time:.2f} ms")
        
        except Exception as e:

            events.request.fire(
                # request_type="RMI",
                # request_type="gRPC",
                request_type="thrift",
                name="Calculate Total",
                response_time=0,
                response_length=0,
                exception=str(e)
            )
            print(f"Error processing request: {e}")

@events.test_stop.add_listener
def on_test_stop(environment, **kwargs):
    if TestUser.start_time is not None and TestUser.end_time is not None:
        elapsed_time = (TestUser.end_time - TestUser.start_time) / 1000  # Tổng thời gian chạy (giây)
        throughput = TestUser.total_requests / elapsed_time if elapsed_time > 0 else 0
        print(f"✅ Throughput (RPS): {throughput:.2f} requests/sec")
    else:
        print("⚠ Không thể tính throughput vì không có start_time hoặc end_time.")
    # try:
    #     TestUser.thrift_client.closeTransport()
    #     print("🚪 Đã đóng kết nối Thrift client thành công.")
    # except Exception as e:
    #     print(f"⚠ Lỗi khi đóng kết nối Thrift client: {e}")