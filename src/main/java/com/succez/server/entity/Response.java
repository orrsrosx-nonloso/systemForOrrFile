package com.orrsrosx.server.entity;

import com.orrsrosx.filesystem.service.FileService;
import com.orrsrosx.utils.configureUtils;
import lombok.Data;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.orrsrosx.server.handler.RequestHandler.notFound404;

/**
 * @author huyun
 * @ClassName 响应类
 * @createTime 2021/3/26 11:56
 * @Version
 **/
@Data
public class Response {

    //成功
    public static Integer SUCCESS = 200;

    //失败
    public static Integer FAILURE = 404;

    //状态信息
    public static String OK = "OK";

    public static String NOT_FOUND = "file not found";

    public static String PRO_PATH = configureUtils.getConfig("PRO_PATH");

    public static String ROOT_P = configureUtils.getConfig("ROOT_P");


    public static String ROOT_Path = configureUtils.getConfig("ROOT_Path");


    public static String ROOT_PathC = configureUtils.getConfig("ROOT_PathC");


    public static final List<String> imgType = Arrays.asList("jpg","png","gif","tif","bmp","psd","ico","bpg","jp2", "jpm","jpx"
    );



    /**
     * 协议版本
     */
    private String version;

    /**
     * 响应状态码 200...
     */
    private int code;

    /**
     * 响应状态 ok...
     */
    private String status;

    /**
     * 响应内容类型
     */
    private String contentType;

    /**
     * 响应内容长度
     */
    private long contentLength;

    /**
     * 响应消息
     */
    private byte[] message;

    /**
     * 输出流
     */
    private OutputStream outputStream;

    public Response(String version, int code, String status, String contentType, byte[] message, OutputStream outputStream) {
        this.version = version;
        this.code = code;
        this.status = status;
        this.contentType = contentType;
        this.message = message;
        this.outputStream = outputStream;
    }

    public Response(String version, int code, String status, String contentType, long contentLength, byte[] message) {
        this.version = version;
        this.code = code;
        this.status = status;
        this.contentType = contentType;
        this.contentLength = contentLength;
        this.message = message;
    }

    public Response(String version, int code, String status, String contentType, long contentLength) {
        this.version = version;
        this.code = code;
        this.status = status;
        this.contentType = contentType;
        this.contentLength = contentLength;
    }

    public Response() {
    }

    /**
     *
     * @author chenmc
     * @Description outputStream数据处理
     * @date 2021/3/30 14:39
     * @param outputStream,request
     * @return Response
     */
    public static Response parserOsMessage(OutputStream outputStream, Request request) {
        return new Response(request.getVersion(), SUCCESS, OK, null, null, outputStream);
    }

    /**
     *
     * @author chenmc
     * @Description 图片链接数据处理
     * @date 2021/3/30 14:40
     * @param response,staticUrl,request
     * @return Response
     */
    public static Response parserImgMessage(Response response,String staticUrl,Request request) throws IOException {

        String str=staticUrl.split("\\.")[1];
        if (imgType.contains(str)) {
            File file = new File(ROOT_Path + staticUrl);
            if (!file.exists() || !file.isFile()) {
                notFound404(response.getOutputStream(), request);
            }
            long fileSize = file.length();

            String fileType = new FileService().getContentType(staticUrl);
            byte[] bytes = Files.readAllBytes(file.toPath());
            response = new Response(request.getVersion(), SUCCESS, OK, fileType, fileSize);
            response.setMessage(bytes);
            response.setStatus(OK);
            response.setCode(SUCCESS);

        }
        return response;
    }


    /**
     *
     * @author chenmc
     * @Description 下载功能信息处理
     * @date 2021/3/30 14:41
     * @param response,Param,request
     * @return Response
     */
    public static Response parserDownloadMessage(Response response, Map<String, String> Param, Request request) throws IOException {

        String pathType =null;
        if (!Param.isEmpty()) {
            pathType = Param.get("path").substring(PRO_PATH.length());
        }
        File file = new File(ROOT_P + pathType);
        if (!file.exists()) {
            notFound404(response.getOutputStream(), request);
        }
        String fileType = new FileService().getContentType(pathType);
        byte[] bytes = Files.readAllBytes(file.toPath());
        long fileSize = file.length();
        response = new Response(request.getVersion(), SUCCESS, OK, fileType, fileSize);
        response.setMessage(bytes);
        response.setStatus(OK);
        response.setCode(SUCCESS);
        return response;
    }

    public static String buildMessage(Request request, Response response) {

        StringBuilder buildMessage = new StringBuilder();
        buildMessage.append(request.getVersion()).append(" ").append(response.getCode()).append(" ").append(response.getStatus()).append("\r\n")
                .append("Content-Type: " + response.getContentType() + ";charset=" + "UTF-8").append("\r\n")
                .append("Content-Length: " + response.getContentLength()).append("\r\n").append("\r\n");
        return buildMessage.toString();

    }

    public static void sendResponse(OutputStream outputStream,byte[] line,String outline) throws IOException {
        //TODO:getBytes增加编码
        outputStream.write(outline.getBytes("UtF-8"));
        outputStream.write(line);
    }

}
