package com.orrsrosx.server.handler.imp;

import com.orrsrosx.server.entity.Request;
import com.orrsrosx.server.entity.Response;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 * @author huyun
 * @ClassName 请求处理
 * @createTime 2021/3/23 16:57
 * @Version
 **/
public interface HttpServlet {

    /**
     * 处理get请求
     * @param request 请求信息
     * @param response 响应信息
     */
    public void doGet (Response response, Request request) throws IOException, InvocationTargetException, IllegalAccessException;


    /**
     * 处理post请求
     * @param request 请求信息
     * @param response 响应信息
     */
    public void doPost (Response response,Request request) throws InvocationTargetException, IllegalAccessException, IOException;
}
