import gbb.rpc.learning.api.HelloService;
import gbb.rpc.learning.registry.DefaultServiceRegistry;
import gbb.rpc.learning.registry.ServiceRegistry;
import gbb.rpc.learning.socket.SocketServer;

/**
 * @author goliang
 * @date 2021/10/10 17:27
 */
public class SocketTestServer {
    public static void main(String[] args) {
        HelloService helloService = new HelloServiceImpl();
        ServiceRegistry serviceRegistry = new DefaultServiceRegistry();
        serviceRegistry.registry(helloService);
        SocketServer socketServer = new SocketServer(serviceRegistry);
        socketServer.start(9000);
    }
}
