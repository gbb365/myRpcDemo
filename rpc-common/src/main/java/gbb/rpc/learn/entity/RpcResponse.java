package gbb.rpc.learn.entity;

import gbb.rpc.learn.enumeration.ResponseCode;
import lombok.Data;


import java.io.Serializable;

/**
 * @author goliang
 * @date 2021/9/25 19:32
 */
@Data
public class RpcResponse <T> implements Serializable {
    private Integer statusCode;
    private String message;
    private T data;


    public static <T> RpcResponse<T> success(T data){
        RpcResponse<T> response = new RpcResponse<>();
        response.setStatusCode(ResponseCode.SUCCESS.getCode());
        response.setData(data);
        return response;
    }

    public static <T> RpcResponse<T> fail(ResponseCode code){
        RpcResponse<T> response = new RpcResponse<>();
        response.setStatusCode(code.getCode());
        response.setMessage(code.getMessage());
        return response;
    }

}
