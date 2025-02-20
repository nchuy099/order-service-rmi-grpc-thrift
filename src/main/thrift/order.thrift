namespace java com.nchuy099.thrift.genJava

struct OrderRequest {
    1: string productId,
    2: i32 quantity
}

struct OrderResponse {
    1: double result
}

service OrderService {
    OrderResponse calculateTotal(1: OrderRequest request)
}