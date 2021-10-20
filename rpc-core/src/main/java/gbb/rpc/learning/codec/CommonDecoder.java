package gbb.rpc.learning.codec;

/**
 * @author goliang
 * @date 2021/10/19 20:57
 */

import gbb.rpc.learn.entity.RpcRequest;
import gbb.rpc.learn.entity.RpcResponse;
import gbb.rpc.learn.enumeration.PackageType;
import gbb.rpc.learn.enumeration.RpcError;
import gbb.rpc.learn.exception.RpcException;
import gbb.rpc.learning.serializer.CommonSerializer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * 通用的解码拦截器
 */
public class CommonDecoder extends ReplayingDecoder {
    private static final Logger logger  = LoggerFactory.getLogger(CommonDecoder.class);
    private static final int MAGIC_NUMBER = 0xCAFEBABE;

//    @Override
//    protected void decode(ChannelHandlerContext ctx, ByteBuf byteBuf, List<Object> list) throws Exception {
//        int magic = byteBuf.readInt();
//        if(magic != MAGIC_NUMBER){
//            LOGGER.info("不能识别的协议包：{}", magic);
//            throw new RpcException(RpcError.UNKNOW_PROTOCOL);
//
//        }
//        int packageInt = byteBuf.readInt();
//        Class<?> packageClass;
//        if(packageInt == PackageType.REQUEST_PAGE.getCode()){
//            packageClass = RpcRequest.class;
//        }else if(packageInt == PackageType.RESPONSE_PAGE.getCode()){
//            packageClass = RpcResponse.class;
//        }else{
//            LOGGER.info("不能识别数据包：{}", packageInt);
//            throw new RpcException(RpcError.UKNOW_PACKAGE_TYPE);
//        }
//        int serializerCode = byteBuf.readInt();
//        CommonSerializer commonSerializer = CommonSerializer.getByCode(serializerCode);
//        if(commonSerializer == null){
//            LOGGER.info("不能识别的序列化器：{}", serializerCode);
//            throw new RpcException(RpcError.UKNOW_SERIALIZER);
//        }
//        int len = byteBuf.readInt();
//        byte[] bytes = new byte[len];
//        byteBuf.readBytes(bytes);
//        Object obj = commonSerializer.deserialize(bytes, packageClass);
//        list.add(obj);
//        }
@Override
protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
    //从缓冲区中读取数据
    int magic = in.readInt();
    if(magic != MAGIC_NUMBER){
        logger.error("不识别的协议包：{}", magic);
        throw new RpcException(RpcError.UNKNOWN_PROTOCOL);
    }
    int packageCode = in.readInt();
    Class<?> packageClass;
    if(packageCode == PackageType.REQUEST_PACK.getCode()){
        packageClass = RpcRequest.class;
    }else if (packageCode == PackageType.RESPONSE_PACK.getCode()){
        packageClass = RpcResponse.class;
    }else {
        logger.error("不识别的数据包：{}", packageCode);
        throw new RpcException(RpcError.UNKNOWN_PACKAGE_TYPE);
    }
    int serializerCode = in.readInt();
    CommonSerializer serializer = CommonSerializer.getByCode(serializerCode);
    if(serializer == null){
        logger.error("不识别的反序列化器：{}", serializerCode);
        throw new RpcException(RpcError.UNKNOWN_SERIALIZER);
    }
    int length = in.readInt();
    byte[] bytes = new byte[length];
    in.readBytes(bytes);
    Object obj = serializer.deserialize(bytes, packageClass);
    //添加到对象列表
    out.add(obj);
}

}
