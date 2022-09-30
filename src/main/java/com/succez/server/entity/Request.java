package com.orrsrosx.server.entity;

import com.alibaba.fastjson.JSONObject;
import com.orrsrosx.server.ThreadTask;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author huyun
 * @ClassName 接收到的http信息
 * @createTime 2021/3/23 16:26
 * @Version
 **/
@Data
public class Request {
    private static final Logger log = LoggerFactory.getLogger(ThreadTask.class);


    private String uri;//请求的uri

    private String method;//请求方法（post/get）

    private String version; //http版本

    private Map<String, String> headers; //参数

    private String message;//请求相关参数

    private Map<String, String> Param;

    private InputStream inputStream;

    public Request(String uri, String method, String version, Map<String, String> headers, String message) {
        this.uri = uri;
        this.method = method;
        this.version = version;
        this.headers = headers;
        this.message = message;
    }

    public Request() {

    }


    public Request parserRequest(InputStream inputStream, Request request) throws IOException {
        byte[] container = new byte[0x100000];
        int intInputStream = inputStream.read(container);
        String resMsg = new String(container, 0, intInputStream);
        String[] resArray = resMsg.split("\r\n");
        String bufferReaderContext = resArray[0];//读取所需Url
        if (bufferReaderContext == null) {
            return null;
        }
        String[] Station = bufferReaderContext.split(" ");
        String Method = Station[0];
        String url = Station[1];
        String version = Station[2];
        request.setMethod(Method);
        request.setUri(url);
        request.setVersion(version);
        handlerMessageParser(resArray, request);
        return request;
    }


    /**
     * @param resArray,request
     * @return void
     * @author chenmc
     * @Description 请求头数据处理
     * @date 2021/3/30 14:39
     */
    private static void handlerMessageParser(String[] resArray, Request request) {
        Map<String, String> headers = new HashMap<>();
        int len = resArray.length;
        String[] str = new String[2];
        for (int i = 1; i <= len - 1; i++) {

            if (i != len - 1) {
                if (resArray[i].contains(":")) {
                    str = resArray[i].split(": ");
                    headers.put(str[0], str[1]);
                }
            }
            if (i == len - 1 && i > 16) {
                JSONObject jsonobject = JSONObject.parseObject(resArray[len - 1]);
                Set<String> key = jsonobject.keySet();
                for (String set : key) {
                    request.setMessage(jsonobject.getString(set));
                }
            }
        }
        request.setHeaders(headers);
    }


    /**
     * @param staticUrl,Param
     * @return String
     * @author chenmc
     * @Description 请求数据处理
     * @date 2021/3/30 14:36
     */
    public static String urlHandler(String staticUrl, Map<String, String> Param) {
        if (staticUrl.contains("?")) {
            String[] stringSplit = staticUrl.split("\\?");
            log.info("为文件路径");
            if (stringSplit[1].contains("=")) {
                String[] agentpath = stringSplit[1].split("=");
                String paramName = agentpath[0];
                String paramValue = agentpath[1];
                Param.put(paramName, paramValue);
                if (agentpath[0].contains("path")) {
                    log.info("获取路径");
                }
            }
            staticUrl = stringSplit[0];
        }
        return staticUrl;
    }
}
