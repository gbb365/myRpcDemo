package gbb.rpc.learning.registry;

/**
 * @author goliang
 * @date 2021/10/10 15:45
 */
public interface ServiceRegistry {
    /**
     * 将一个服务注册到注册表
     * @param service 待注册的服务实体
     * @param <T> 服务实体类
     */
    <T>void registry(T service);

    /**
     * 根据服务名获取服务实体
     * @param serviceName
     * @return
     */
    Object getService(String serviceName);
}
