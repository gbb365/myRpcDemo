import gbb.rpc.learning.api.HelloObject;
import gbb.rpc.learning.api.HelloService;
import lombok.extern.slf4j.Slf4j;

/**
 * @author goliang
 * @date 2021/9/25 20:33
 */
@Slf4j
public class HelloServiceImpl implements HelloService {

    @Override
    public String hello(HelloObject helloObject) {
        log.info("接收到：{}", helloObject.getMessage() );
        return "这是调用后的返回值， id ：" + helloObject.getId();
    }
}
