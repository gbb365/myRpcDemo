package gbb.rpc.learning.client;

import gbb.rpc.learn.entity.RpcRequest;
import gbb.rpc.learn.entity.RpcResponse;
import gbb.rpc.learn.enumeration.ResponseCode;
import gbb.rpc.learn.enumeration.RpcError;
import gbb.rpc.learn.exception.RpcException;
import lombok.extern.java.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * @author goliang
 * @date 2021/9/25 19:47
 */
public class RpcClient {
    private static final Logger logger = LoggerFactory.getLogger(RpcClient.class);

    //打开输入输出流，写入输出流，从输入流返回
    public Object sendRequest(RpcRequest rpcRequest, String host, int port){
        try (Socket socket = new Socket(host,port)){
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());


            objectOutputStream.writeObject(rpcRequest);
            objectOutputStream.flush();
//            return  objectInputStream.readObject();
            RpcResponse response = (RpcResponse) objectInputStream.readObject();
            if(rpcRequest == null) {
                logger.error("服务调用失败，service:{}" + rpcRequest.getInterfaceName());
                throw new RpcException(RpcError.SERVICE_INVOCATION_FAILURE, "service:" + rpcRequest.getInterfaceName());
            }
            if(response.getStatusCode() == null|| response.getStatusCode() != ResponseCode.SUCCESS.getCode()){
                logger.error("服务调用失败， service{}" + rpcRequest.getInterfaceName());
                throw new RpcException(RpcError.SERVICE_INVOCATION_FAILURE, "service:" + rpcRequest.getInterfaceName());
            }
            return response.getData();
        }catch (IOException | ClassCastException | ClassNotFoundException e){
            logger.error("调用时有错误发生", e);
//            return  null;
            throw  new RpcException("服务调用失败：", e);
        }
    }

}
