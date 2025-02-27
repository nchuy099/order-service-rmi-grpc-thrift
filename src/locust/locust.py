from py4j.java_gateway import JavaGateway

gateway = JavaGateway()

thrift_client = gateway.entry_point

resp = thrift_client.request("1", 3)

print(resp)
print("response time: ", (resp["end_time"] - resp["start_time"]) / 1000)