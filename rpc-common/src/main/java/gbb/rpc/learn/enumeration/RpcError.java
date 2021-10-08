package gbb.rpc.learn.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author goliang
 * @date 2021/10/8 21:24
 */
@AllArgsConstructor
@Getter
public enum RpcError {

    SERVICE_INVOCATION_FAILURE("服务调用实现失败"),
    SERVICE_CAN_NOT_BE_NULL("注册服务不能为空"),
    SERVICE_NOT_FOUND("找不到对应的服务"),
    SERVICE_NOT_IMPLEMENT_ANY_INTRFACE("注册的服务未实现接口");
    private  final String message;
}
