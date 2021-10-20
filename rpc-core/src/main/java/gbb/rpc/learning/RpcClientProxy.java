package gbb.rpc.learning;

import gbb.rpc.learn.entity.RpcRequest;
import gbb.rpc.learning.socket.SocketClient;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author goliang
 * @date 2021/9/25 19:58
 */

public class RpcClientProxy implements InvocationHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(RpcClientProxy.class);
//    private String host;
//    private int port;
    private final RpcClient rpcClient;
    public RpcClientProxy(RpcClient rpcClient){
        this.rpcClient = rpcClient;
    }

    @SuppressWarnings("unchecked")
    public <T> T getProxy(Class<T> clazz){
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, this);



    }

//    @Override
//    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
//        RpcRequest rpcRequest = RpcRequest.builder().interfaceName(method.getDeclaringClass().getName())
//                .methodName(method.getName()).parameters(args).paramsType(method.getParameterTypes()).build();
//        RpcClient rpcClient = new RpcClient();
////        return ((RpcResponse)rpcClient.sendRequest(rpcRequest,host, port)).getData();
////        return method.invoke(proxy, args);
//        return ((RpcResponse) rpcClient.sendRequest(rpcRequest, host, port)).getData();
//    }
@Override
public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        LOGGER.info("调用方法：{}#{}" + method.getDeclaringClass().getName(), method.getName());
//        RpcRequest rpcRequest = RpcRequest.builder()
//                .interfaceName(method.getDeclaringClass().getName())
//                .methodName(method.getName())
//                .parameters(args)
//                .paramsType(method.getParameterTypes())
//                .build();
//        gbb.rpc.learning.client.RpcClient rpcClient = new RpcClient();
//    //    return ((RpcResponse) rpcClient.sendRequest(rpcRequest, host, port)).getData();
//        return rpcClient.sendRequest(rpcRequest, host, port);
        RpcRequest rpcRequest = new RpcRequest(method.getDeclaringClass().getName(),
                method.getName(), args, method.getParameterTypes());
        return rpcClient.sendRequest(rpcRequest);
    }
}
