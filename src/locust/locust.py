from py4j.java_gateway import JavaGateway

gateway = JavaGateway()

rmi_client = gateway.entry_point

resp = rmi_client.request("1", 3)

print(resp)
print("response time: ", (resp["end_time"] - resp["start_time"]) / 1000)