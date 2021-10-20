package gbb.rpc.learning.serializer;

/**
 * @author goliang
 * @date 2021/10/19 21:15
 */

/**
 * 通用的序列化，反序列化接口
 */
public interface CommonSerializer  {
    byte[] serialize(Object object);
    Object deserialize(byte[] bytes, Class<?> clazz);
    int getCode();

    static CommonSerializer getByCode(int code){
        switch (code){
            case 1:
                return new JsonSerializer();

            default:
                return null;
        }
    }

}
