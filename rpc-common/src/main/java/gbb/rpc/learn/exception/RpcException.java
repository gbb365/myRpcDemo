package gbb.rpc.learn.exception;

import gbb.rpc.learn.enumeration.RpcError;

/**
 * @author goliang
 * @date 2021/10/8 21:29
 */
public class RpcException extends RuntimeException{

    public RpcException(RpcError error, String detail){
        super(error.getMessage()+ ":" + detail);
    }
    public RpcException(String message, Throwable cause){
        super(message, cause);
    }
    public RpcException(RpcError error){
        super(error.getMessage());
    }
}
