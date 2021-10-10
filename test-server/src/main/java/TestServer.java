import gbb.rpc.learning.api.HelloService;
import gbb.rpc.learning.registry.DefaultServiceRegistry;
import gbb.rpc.learning.registry.ServiceRegistry;
import gbb.rpc.learning.server.RpcServer;

/**
 * @author goliang
 * @date 2021/9/25 20:33
 */
public class TestServer {
    public static void main(String[] args) {
//        HelloService helloService = new HelloServiceImpl();
//        RpcServer rpcServer = new RpcServer();
//        rpcServer.register(helloService,9000);
          HelloService helloService = new HelloServiceImpl();
          ServiceRegistry serviceRegistry = new DefaultServiceRegistry();
          serviceRegistry.registry(helloService);
          RpcServer rpcServer = new RpcServer(serviceRegistry);
          rpcServer.start(9000);
    }
}
