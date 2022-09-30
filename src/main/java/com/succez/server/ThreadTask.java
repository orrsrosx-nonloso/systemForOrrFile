package com.orrsrosx.server;

import com.orrsrosx.server.entity.Request;
import com.orrsrosx.server.entity.Response;
import com.orrsrosx.server.handler.RequestHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;


/**
 * @author huyun
 * @ClassName
 * @createTime 2021/1/28 10:57
 * @Version
 **/
public class ThreadTask implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(ThreadTask.class);

    private final Socket socket;

    public Request request = new Request();

    public Response response = new Response();

    public ThreadTask(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (InputStream inputStream = socket.getInputStream(); //输入流对象
             OutputStream outputStream = socket.getOutputStream() //输出流对象)
        ) {
            request = request.parserRequest(inputStream,request);
            response = response.parserOsMessage(outputStream, request);
            switch (request.getMethod()) {
                //get请求
                case "GET":
                    if (request.getVersion().equals("HTTP/1.1")) {
                        new RequestHandler().doGet(response, request);
                    } else {
                        log.info("HTTP版本不符合");
                    }
                    break;
                //post请求
                case "POST":
                    if (request.getVersion().equals("HTTP/1.1")) {
                        new RequestHandler().doPost(response, request);
                    } else {
                        log.info("HTTP版本不符合");
                    }
                    break;
                default:
                    log.info("此请求功能暂时未实现！");
                    break;
            }
        } catch (Exception e) {
            log.error("服务器异常：", e);
        }
    }
}