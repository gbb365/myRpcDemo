import gbb.rpc.learning.api.HelloObject;
import gbb.rpc.learning.api.HelloService;
import gbb.rpc.learning.registry.DefaultServiceRegistry;
import gbb.rpc.learning.registry.ServiceRegistry;

/**
 * @author goliang
 * @date 2021/10/20 20:37
 */
public class NettyTestServer {
    public static void main(String[] args) {
        HelloService service = new HelloServiceImpl();
        ServiceRegistry registry = new DefaultServiceRegistry();
        registry.registry(service);
        NettyServer nettyServer = new NettyServer();
        nettyServer.start(9999);

    }
}
