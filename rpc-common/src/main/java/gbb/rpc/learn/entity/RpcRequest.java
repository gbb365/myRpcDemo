package gbb.rpc.learn.entity;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @author goliang
 * @date 2021/9/25 19:27
 */
@Data
@Builder
public class RpcRequest implements Serializable {
    private String interfaceName;
    private String methodName;
    private Object[] parameters;
    private Class<?>[] paramsType;

}
