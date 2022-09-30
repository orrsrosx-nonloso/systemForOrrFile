package com.orrsrosx.server.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.orrsrosx.filesystem.controller.FileController;
import com.orrsrosx.filesystem.service.FileService;
import com.orrsrosx.server.ThreadTask;
import com.orrsrosx.server.entity.Request;
import com.orrsrosx.server.entity.Response;
import com.orrsrosx.server.handler.imp.HttpServlet;
import com.orrsrosx.utils.configureUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;


/**
 * @author huyun
 * @ClassName 请求处理
 * @createTime 2021/3/3 14:16
 * @Version
 **/
public class RequestHandler implements HttpServlet {

    private static String PRO_PATH = configureUtils.getConfig("PRO_PATH");

    private static String ROOT_P = configureUtils.getConfig("ROOT_P");

    private static String ROOT_Path = configureUtils.getConfig("ROOT_Path");

    private static String STATIC_PATH = configureUtils.getConfig("STATIC_PATH");

    private static String ROOT_PathC = configureUtils.getConfig("ROOT_PathC");

    private static String PROJECT_ROOT = configureUtils.getConfig("PROJECT_ROOT");

    private static final Logger log = LoggerFactory.getLogger(ThreadTask.class);

    private static final Map<String, Method> REQUEST_MAP = new HashMap<>();

    private static final String local="/files.html";


    public static final FileController fileController = new FileController();

    public static final ObjectMapper objectMapper = new ObjectMapper();


    static {
        Locale locale1 = new Locale("zh", "CN");
        ResourceBundle res = ResourceBundle.getBundle("config.map", locale1);
        Enumeration<String> enumeration = res.getKeys();
        List<String> method33 = new ArrayList<>();
        while (enumeration.hasMoreElements()) {
            method33.add(enumeration.nextElement());
        }
        Class<?> classMethod = null;
        try {
            classMethod = Class.forName(res.getString("className"));
        } catch (ClassNotFoundException e) {
            log.error("Class未获取：", e);
        }
        Method[] method2 = classMethod.getMethods();
        for (Method method1 : method2) {
            if (method33.contains(method1.getName())) {
                REQUEST_MAP.put(res.getString(method1.getName()), method1);
            }
        }
    }

    /**
     * @Description: 处理GET请求
     * @Param:
     * @return:
     * @Author: huyun
     * @Date: 2021/1/28
     */
    public void doGet(Response response, Request request) throws IOException, InvocationTargetException, IllegalAccessException {

        Map<String, Method> sq = REQUEST_MAP;
        //处理其他功能请求
        OutputStream outputStream = response.getOutputStream();
        String staticUrl = request.getUri();
        Map<String, String> Param = new HashMap<>();
        staticUrl = request.urlHandler(staticUrl, Param);
        //文件下载
        Set<String> set = REQUEST_MAP.keySet();


        //首页处理和静态路径处理
        //TODO:files.html定义为常量，，，重定向，至8080首页
        if ("/".equals(staticUrl) || staticUrl.startsWith(STATIC_PATH)) {
            // 请求的是static目录下的静态资源
            String str = staticUrl.length() > 1 ? staticUrl.substring(STATIC_PATH.length()) :local ; //获取静态资源
            String project_root = PROJECT_ROOT;//获取项目本地路径
            getMapOfHtml(project_root + STATIC_PATH, str.isEmpty() ? "/" : str, outputStream, request);
            return;
        } else if (staticUrl.contains(PRO_PATH)) {
            response=response.parserImgMessage(response,staticUrl,request);
            String resLine = response.buildMessage(request, response);
            response.sendResponse(outputStream, response.getMessage(), resLine);
            return;
        } else if (set.contains(staticUrl)) {
            Method method = REQUEST_MAP.get(staticUrl);
            if (method != null) {
                //反射执行方法
                Object object = method.invoke(fileController, Param);
                String resend = objectMapper.writeValueAsString(object);
                response = new Response(request.getVersion(), response.SUCCESS, response.OK, "application/json", resend.getBytes().length);
                response.setMessage(resend.getBytes(StandardCharsets.UTF_8));
                response.setStatus(response.OK);
                response.setCode(response.SUCCESS);
                String resLine = response.buildMessage(request, response);
                response.sendResponse(outputStream, response.getMessage(), resLine);
            }
            return;
        } else {
            response=response.parserDownloadMessage(response,Param,request);
            String resLine = response.buildMessage(request,response);
            response.sendResponse(outputStream, response.getMessage(), resLine);
            return;
        }
    }

    /**
     *
     * @author chenmc
     * @Description 未找到文件
     * @date 2021/3/30 14:34
     * @param os,request
     * @return void
     */
    public static void notFound404(OutputStream os, Request request) throws IOException {
        log.info("文件不存在");
        byte[] bytes = "<h1 >WE Not Found 404</h1>\n".getBytes(StandardCharsets.UTF_8);
        Response response=new Response("HTTP/1.1", Response.FAILURE, Response.NOT_FOUND,"text/html",bytes.length);
        String httpMsg = response.buildMessage(request, response);
        response.sendResponse(os, bytes, httpMsg);
    }

    /**
     *
     * @author chenmc
     * @Description 初始化情况
     * @date 2021/3/30 14:35
     * @param basePath,path,os,request
     * @return void
     */
    private static void getMapOfHtml(String basePath, String path, OutputStream os, Request request) throws IOException {
        // 创建文件对象
        File file = new File(basePath + path);
        // 文件不存在
        if (!file.exists() || !file.isFile()) {
            notFound404(os, request);
            return;
        }
        long fileSize = file.length();
        String fileType = new FileService().getContentType(path);
        Response response=new Response("HTTP/1.1",Response.SUCCESS,Response.OK,fileType,fileSize);
        response.sendResponse(os, Files.readAllBytes(file.toPath()), response.buildMessage(request,response));
    }


    /**
     *
     * @author chenmc
     * @Description 处理Post请求
     * @date 2021/3/30 14:35
     * @param response,request
     * @return void
     */
    public void doPost(Response response, Request request) throws InvocationTargetException, IllegalAccessException, IOException {
        String staticUrl = request.getUri();
        OutputStream outputStream = response.getOutputStream();
        Map<String, String> Param = new HashMap<>();
        staticUrl = request.urlHandler(staticUrl, Param);

        Method method = REQUEST_MAP.get(staticUrl);
        if (method != null) {
            Object object = method.invoke(fileController, Param, request.getMessage());
            String resend = objectMapper.writeValueAsString(object);
            response=new Response("HTTP/1.1",Response.SUCCESS,Response.OK,"application/json",resend.getBytes().length);
            String resLine = response.buildMessage(request,response);
            response.sendResponse(outputStream, resend.getBytes(StandardCharsets.UTF_8), resLine);
        }
    }
}
