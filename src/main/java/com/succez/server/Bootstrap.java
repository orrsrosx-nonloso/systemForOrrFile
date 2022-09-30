package com.orrsrosx.server;

import com.orrsrosx.utils.configureUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * @author huyun
 * @ClassName 启动类
 * @createTime 2021/1/28 10:49
 * @Version
 **/
public class Bootstrap {

    private static final Logger log = LoggerFactory.getLogger(Bootstrap.class);



    public void startServer() throws IOException {
        //可缓存线程池
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
        int port= Integer.parseInt(configureUtils.getConfig("port"));;
        try {
            @SuppressWarnings("resource")
            ServerSocket serverSocket = new ServerSocket(port);
            while (true) {
                //服务器套接字
                Socket socket = serverSocket.accept();
                ThreadTask request = new ThreadTask(socket);
                cachedThreadPool.execute(request);
            }
        } catch (IOException e) {
            log.error("We can not to find the sources of 8080:", e);
        }
    }

    public static void main(String[] args) throws IOException {
        log.info("启动服务器，监听端口：" + configureUtils.getConfig("port"));
        new Bootstrap().startServer();
    }
}
