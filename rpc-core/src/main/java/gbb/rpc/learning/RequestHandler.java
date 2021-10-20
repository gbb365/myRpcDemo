package gbb.rpc.learning;

import gbb.rpc.learn.entity.RpcRequest;
import gbb.rpc.learn.entity.RpcResponse;
import gbb.rpc.learn.enumeration.ResponseCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author goliang
 * @date 2021/9/25 20:17
 */

/**
 * 实际执行方法调用的处理器
 */
//public class RequestHandler implements Runnable {
public class RequestHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(RequestHandler.class);

    public Object handle(RpcRequest rpcRequest, Object service) throws InvocationTargetException, IllegalAccessException {
//        return null;
        Object result = null;
        try {
            result = invokeTargetMethod(rpcRequest, service);
            //一个接口（服务）内可以有多个方法
            LOGGER.info("服务：{}成功调用方法：{}", rpcRequest.getInterfaceName(),rpcRequest.getMethodName());
        }catch (IllegalAccessException e){
            LOGGER.info("调用或发送时有错误发生："+ e);
        }
        return result;
    }
    public Object invokeTargetMethod(RpcRequest rpcRequest, Object service) throws InvocationTargetException, IllegalAccessException {
        Method method = null;
        try {
            method = service.getClass().getMethod(rpcRequest.getMethodName(),rpcRequest.getParamsType());
        }catch (NoSuchMethodException e){
//            return RpcResponse.fail(ResponseCode.NOT_FOUND_METHOD);
            LOGGER.info("调用时有错误发生：" + e);
        }
        return method.invoke(service,rpcRequest.getParameters());
    }

//    private Socket socket;
//    private Object service;
//    public RequestHandler(Socket socket, Object service){
//        this.socket = socket;
//        this.service = service;
//    }

//    @Override
//    public void run() {
//        try (
//                ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
//                ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream())
//             ){
//            RpcRequest rpcRequest = (RpcRequest) objectInputStream.readObject();
////            Method method = service.getClass().
////                    getMethod(rpcRequest.getMethodName(),rpcRequest.getParamsType());
////            Object returnObject = method.invoke(service, rpcRequest.getParameters());
//            Object returnObject = invokeMethod(rpcRequest);
//            objectOutputStream.writeObject(RpcResponse.success(returnObject));
//            objectOutputStream.flush();
//
//        }catch (IOException | ClassNotFoundException | InvocationTargetException | IllegalAccessException e){
//            LOGGER.error("调用时有错误发生");
//        }
//    }

//    private Object invokeMethod(RpcRequest rpcRequest) throws ClassNotFoundException, InvocationTargetException, IllegalAccessException {
//        Class<?> clazz = Class.forName(rpcRequest.getInterfaceName());
//        if(!clazz.isAssignableFrom(service.getClass())){
//            return RpcResponse.fail(ResponseCode.NOT_FOUND_CLASS);
//        }
//        Method method ;
//        try {
//            method = service.getClass().getMethod(rpcRequest.getMethodName(), rpcRequest.getParamsType());
//        }catch (NoSuchMethodException e){
//            return RpcResponse.fail(ResponseCode.NOT_FOUND_METHOD);
//        }
//        return method.invoke(service, rpcRequest.getParameters());
//    }

}
