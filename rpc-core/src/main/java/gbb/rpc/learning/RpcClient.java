package gbb.rpc.learning;

import gbb.rpc.learn.entity.RpcRequest;

/**
 * @author goliang
 * @date 2021/10/10 17:07
 */
public interface RpcClient {
    Object sendRequest(RpcRequest rpcRequest);

}
