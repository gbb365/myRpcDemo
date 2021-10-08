package gbb.rpc.learning.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

/**
 * @author goliang
 * @date 2021/9/25 20:08
 */
public class RpcServer {
    private static final Logger logger = LoggerFactory.getLogger(RpcServer.class);
    private static ExecutorService threadPool;
    public RpcServer(){
        int corePoolSize = 5;
        int maxmunPoolSize = 50;
        long keepAliveTime = 60;
//        BlockingDeque<Runnable> workingQueue = new ArrayBlockingQueue<Object>(100);
        BlockingQueue<Runnable> workingQueue = new ArrayBlockingQueue<>(100);
        ThreadFactory threadFactory = Executors.defaultThreadFactory();
        threadPool = new ThreadPoolExecutor(corePoolSize,maxmunPoolSize,keepAliveTime,
                TimeUnit.SECONDS,workingQueue, threadFactory);
    }


//    public void register(Object service, int port){
//        try(ServerSocket serverSocket = new ServerSocket(port)) {
//            logger.info("服务正在启动");
//            Socket socket1 ;
//            while((socket1 = serverSocket.accept()) != null){
//                logger.info("客户端连接， Ip为： " + socket1.getInetAddress());
//                threadPool.execute(new WorkerThread(socket1, service));
//            }
//        } catch (IOException e) {
//            logger.error("连接出现错误");
//        }
//    }
public void register(Object service, int port) {
    try (ServerSocket serverSocket = new ServerSocket(port)) {
        logger.info("服务器正在启动...");
        Socket socket;
        while((socket = serverSocket.accept()) != null) {
            logger.info("客户端连接！Ip为：" + socket.getInetAddress());
            threadPool.execute(new WorkerThread(socket, service));
        }
    } catch (IOException e) {
        logger.error("连接时有错误发生：", e);
    }
}
}
