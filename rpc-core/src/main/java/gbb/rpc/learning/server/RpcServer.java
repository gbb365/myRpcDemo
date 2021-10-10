package gbb.rpc.learning.server;

import gbb.rpc.learning.registry.ServiceRegistry;
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
    private static final int CORE_POOL_SIZE = 5;
    private static final int MAXMUM_POOL_SIZE = 50;
    private static final int KEEP_ALIVE_TIME = 60;
    private static final int BLOCKING_QUEUE_CAPACITY = 100;
    private final ServiceRegistry serviceRegistry;
    private final ExecutorService threadPool;
    private RequestHandler requestHandler = new RequestHandler();

    public RpcServer(ServiceRegistry serviceRegistry){
        this.serviceRegistry = serviceRegistry;
        BlockingQueue<Runnable> workingQueue = new ArrayBlockingQueue<>(BLOCKING_QUEUE_CAPACITY);
        ThreadFactory threadFactory = Executors.defaultThreadFactory();
        threadPool = new ThreadPoolExecutor(CORE_POOL_SIZE, MAXMUM_POOL_SIZE, KEEP_ALIVE_TIME,
                TimeUnit.SECONDS, workingQueue, threadFactory);

    }

    public void start(int port){
        try(ServerSocket serverSocket = new ServerSocket(port)){
            logger.info("服务启动...");
            Socket socket;
            while((socket = serverSocket.accept()) != null){
                logger.info("客户端连接！{}#{}", socket.getInetAddress(),socket.getPort());
                threadPool.execute(new RequestHandlerThread(socket, requestHandler, serviceRegistry));
            }
                threadPool.shutdown();
        } catch (IOException e) {
//            e.printStackTrace();
            logger.info("服务器启动时有错误发生：" + e);
        }
    }

//    private static ExecutorService threadPool;
//    public RpcServer(){
//        int corePoolSize = 5;
//        int maxmunPoolSize = 50;
//        long keepAliveTime = 60;
////        BlockingDeque<Runnable> workingQueue = new ArrayBlockingQueue<Object>(100);
//        BlockingQueue<Runnable> workingQueue = new ArrayBlockingQueue<>(100);
//        ThreadFactory threadFactory = Executors.defaultThreadFactory();
//        threadPool = new ThreadPoolExecutor(corePoolSize,maxmunPoolSize,keepAliveTime,
//                TimeUnit.SECONDS,workingQueue, threadFactory);
//    }


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
//public void register(Object service, int port) {
//    try (ServerSocket serverSocket = new ServerSocket(port)) {
//        logger.info("服务器正在启动...");
//        Socket socket;
//        while((socket = serverSocket.accept()) != null) {
////            logger.info("客户端连接！Ip为：" + socket.getInetAddress());
////            threadPool.execute(new RequestHandler(socket, service));
//            logger.info("客户端连接！ Ip：" + socket.getInetAddress()+":" + socket.getPort());
//            threadPool.execute(new RequestHandler(socket,service));
//        }
//    } catch (IOException e) {
//        logger.error("连接时有错误发生：", e);
//    }
//}
}
