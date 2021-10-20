package gbb.rpc.learning.registry;

import gbb.rpc.learn.enumeration.RpcError;
import gbb.rpc.learn.exception.RpcException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;


/**
 * @author goliang
 * @date 2021/10/10 15:48
 */
public class DefaultServiceRegistry implements ServiceRegistry{
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultServiceRegistry.class);

    // key - 服务名(接口名)， val- 服务实体（实现类的实例对象)
    private static final Map<String, Object> serviceMap = new ConcurrentHashMap<>();
    //存放实现类的名称
    private static final Set<String> registeredService = ConcurrentHashMap.newKeySet();
    @Override
    public <T> void registry(T service) {
        String serviceImplName = service.getClass().getCanonicalName();
        if(registeredService.contains(serviceImplName)){
            return;
        }
        registeredService.add(serviceImplName);
        Class<?> [] interfaces = service.getClass().getInterfaces();
        if(interfaces.length == 0){
            throw new RpcException(RpcError.SERVICE_NOT_IMPLEMENT_ANY_INTRFACE);
        }
        //一个服务可能实现了多个接口， 这里把多个接口和服务一一对应（多对一）
        for(Class<?> i : interfaces){
            //service 是具体服务的实现类对象
            serviceMap.put(i.getCanonicalName(), service);
        }
        LOGGER.info("向接口: {}, 注册服务: {}", interfaces, serviceImplName);
    }
    //根据服务的接口名返回 接口实现类对象
    @Override
    public Object getService(String serviceName) {
        Object service =  serviceMap.get(serviceName);
        if(service == null){
            throw new RpcException(RpcError.SERVICE_NOT_FOUND);
        }
        return service;
    }
}
