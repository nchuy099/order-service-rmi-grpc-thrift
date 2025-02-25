from locust import User, task, between
from py4j.java_gateway import JavaGateway, GatewayParameters

class RMIUser(User):
    wait_time = between(1, 3)  # Thời gian chờ giữa các request

    def on_start(self):
        """Kết nối đến Py4J Gateway khi test bắt đầu"""
        self.gateway = JavaGateway(gateway_parameters=GatewayParameters(address="localhost", port=25333))
        self.rmi_client = self.gateway.entry_point  # Gọi đối tượng RMIClient từ Java

    @task
    def test_calculate_total(self):
        """Gửi request đến RMIClient.run"""
        product_id = "1"
        quantity = 10
        try:
            total = self.rmi_client.run(product_id, quantity)  # Gọi hàm run() trong Java
            print(f"Total cost: {total}")
        except Exception as e:
            print(f"Error: {e}")

    def on_stop(self):
        """Đóng kết nối khi test kết thúc"""
        self.gateway.close()

if __name__ == "__main__":
    import os
    os.system("locust -f locustfile.py")
