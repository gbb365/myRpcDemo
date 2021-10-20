import gbb.rpc.learn.entity.RpcRequest;
import gbb.rpc.learn.entity.RpcResponse;
import gbb.rpc.learning.RpcClient;
import gbb.rpc.learning.codec.CommomEncoder;
import gbb.rpc.learning.codec.CommonDecoder;
import gbb.rpc.learning.serializer.JsonSerializer;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.jar.Attributes;

/**
 * @author goliang
 * @date 2021/10/19 21:33
 */
public class NettyClient implements RpcClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(NettyClient.class);
    private int port;
    private String ip;
    public NettyClient(String ip, int port){
        this.ip = ip;
        this.port = port;
    }
    private static final Bootstrap bootstrap ;
    //初始化
    static {
        bootstrap = new Bootstrap();
        NioEventLoopGroup nioEventLoopGroup = new NioEventLoopGroup();
        bootstrap.group(nioEventLoopGroup).channel(NioSocketChannel.class).option(ChannelOption.SO_KEEPALIVE, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        ChannelPipeline pipeline = socketChannel.pipeline();
                        pipeline.addLast(new CommonDecoder())
                                .addLast(new CommomEncoder(new JsonSerializer()))
                                .addLast(new NettyClientHandler());

                    }
                });
    }

    @Override
    public Object sendRequest(RpcRequest rpcRequest) {
        try {
            ChannelFuture future = bootstrap.connect(ip, port).sync();
            LOGGER.info("客户端已连接到服务端{}：{}",ip,port );
            Channel channel = future.channel();
            if(channel != null){
                channel.writeAndFlush(rpcRequest).addListener(future1->{
                    if(future1.isSuccess()){
                        LOGGER.info(String.format("客户端发送消息：%S", rpcRequest.toString()));
                    }else {
                        LOGGER.info("调用时有错误发生：", future1.cause());
                    }
                });
            }
            channel.closeFuture().sync();
            //AttributeMap<AttributeKey, AttributeValue>是绑定在Channel上的，可以设置用来获取通道对象
            AttributeKey<RpcResponse> key = AttributeKey.valueOf("rpcResponse");
            RpcResponse response = channel.attr(key).get();
            return response.getData();
        }catch (InterruptedException e){

            LOGGER.info("调用时有错误发生: ",e);
        }
        return null;

    }
}
