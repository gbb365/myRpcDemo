package gbb.rpc.learning.server;

import gbb.rpc.learn.entity.RpcRequest;
import gbb.rpc.learn.entity.RpcResponse;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author goliang
 * @date 2021/9/25 20:17
 */
@AllArgsConstructor
public class WorkerThread implements Runnable {
    private static final Logger LOGGER = LoggerFactory.getLogger(WorkerThread.class);
    private Socket socket;
    private Object service;
    public WorkerThread(Socket socket, Object service){
        this.socket = socket;
        this.service = service;
    }

    @Override
    public void run() {
        try (
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream())
             ){
            RpcRequest rpcRequest = (RpcRequest) objectInputStream.readObject();
            Method method = service.getClass().
                    getMethod(rpcRequest.getMethodName(),rpcRequest.getParamsType());
            Object returnObject = method.invoke(service, rpcRequest.getParameters());
            objectOutputStream.writeObject(RpcResponse.success(returnObject));
            objectOutputStream.flush();

        }catch (IOException | IllegalAccessException| NoSuchMethodException| ClassNotFoundException | InvocationTargetException e){
            LOGGER.error("调用时有错误发生");
        }
    }

}
