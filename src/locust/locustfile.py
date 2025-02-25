from locust import User, task, between, events
from py4j.java_gateway import JavaGateway
import random

gateway = JavaGateway()

class RMITestUser(User):
    wait_time = between(0, 0)
    total_requests = 0
    start_time = None
    end_time = None

    @task 
    def test(self):
        product_id = str(random.randint(1, 1000))
        quantity = random.randint(1,100)
        try:
            rmi_client = gateway.entry_point
            resp = rmi_client.request(product_id, quantity)
            
            success = resp["status"]
            result = resp.get("result", None)
            start = resp["start_time"]
            end = resp["end_time"]
            response_time = (end - start)
            
            if success:
                RMITestUser.total_requests += 1
                if RMITestUser.start_time is None or start < RMITestUser.start_time:
                    RMITestUser.start_time = start
                if RMITestUser.end_time is None or end > RMITestUser.end_time:
                    RMITestUser.end_time = end
        
            events.request.fire(
                request_type="RMI",
                name="Calculate Total",
                response_time=response_time,
                response_length=len(str(result)) if success else 0,
                exception=None if success else "Request failed"
            )
            print(f"Product: {product_id}, Quantity: {quantity}, Cost: {result}, Success: {success}, Response Time: {response_time:.2f} ms")
        
        except Exception as e:

            events.request.fire(
                request_type="RMI",
                name="Calculate Total",
                response_time=0,
                response_length=0,
                exception=str(e)
            )
            print(f"Error processing request: {e}")

@events.test_stop.add_listener
def on_test_stop(environment, **kwargs):
    if RMITestUser.start_time is not None and RMITestUser.end_time is not None:
        elapsed_time = (RMITestUser.end_time - RMITestUser.start_time) / 1000  # Tổng thời gian chạy (giây)
        throughput = RMITestUser.total_requests / elapsed_time if elapsed_time > 0 else 0
        print(f"✅ Throughput (RPS): {throughput:.2f} requests/sec")
    else:
        print("⚠ Không thể tính throughput vì không có start_time hoặc end_time.")