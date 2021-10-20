package gbb.rpc.learning.codec;

import com.sun.source.tree.PackageTree;
import gbb.rpc.learn.entity.RpcRequest;
import gbb.rpc.learn.enumeration.PackageType;
import gbb.rpc.learning.serializer.CommonSerializer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author goliang
 * @date 2021/10/19 21:21
 */

/**
 * 通用的编码拦截器,将对象编码用编码器编码（这里只是得到一个字节数组）后写入缓冲区，
 * 此处的encode会根据预先定义的协议，把信息写入缓冲区
 */
public class CommomEncoder extends MessageToByteEncoder {
    private static  final int MAGIC_NUMBER = 0xCAFEBABE;

    //需要序列化器来进行编码工作
    private final CommonSerializer serializer;

    public CommomEncoder(CommonSerializer serializer){
        this.serializer = serializer;
    }


    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Object msg, ByteBuf byteBuf) throws Exception {
        byteBuf.writeInt(MAGIC_NUMBER);
        if(msg instanceof RpcRequest){
            byteBuf.writeInt(PackageType.REQUEST_PACK.getCode());
        }else {
            byteBuf.writeInt(PackageType.RESPONSE_PACK.getCode());
        }
        byteBuf.writeInt(serializer.getCode());
        byte[] bytes = serializer.serialize(msg);
        byteBuf.writeInt(bytes.length);
        byteBuf.writeBytes(bytes);
    }
}
