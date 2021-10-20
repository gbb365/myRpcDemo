import gbb.rpc.learning.RpcClientProxy;
import gbb.rpc.learning.api.HelloObject;
import gbb.rpc.learning.api.HelloService;
import gbb.rpc.learning.socket.SocketClient;

/**
 * @author goliang
 * @date 2021/10/10 17:24
 */
public class SocketTestClient {
    public static void main(String[] args) {
        SocketClient client = new SocketClient("127.0.0.1", 9000);
        RpcClientProxy proxy = new RpcClientProxy(client);
        //创建代理对象
        HelloService helloService = proxy.getProxy(HelloService.class);
        //接口方法的参数对象
        HelloObject object = new HelloObject(12, "This is test message");
        //由动态代理可知，代理对象调用hello()实际会执行invoke()
        String res = helloService.hello(object);
        System.out.println(res);
    }
}
