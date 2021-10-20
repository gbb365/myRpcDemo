import gbb.rpc.learning.RpcClient;
import gbb.rpc.learning.RpcClientProxy;
import gbb.rpc.learning.api.HelloObject;
import gbb.rpc.learning.api.HelloService;

/**
 * @author goliang
 * @date 2021/10/20 20:32
 */
public class NettyTestClient {
    public static void main(String[] args) {
        RpcClient rpcClient = new NettyClient("127.0.0.1", 9999);
        RpcClientProxy proxy = new RpcClientProxy(rpcClient);
        HelloService service = proxy.getProxy(HelloService.class);
        HelloObject object = new HelloObject(12," this is netty style");
        String res = service.hello(object);
        System.out.println(res);
    }
}
