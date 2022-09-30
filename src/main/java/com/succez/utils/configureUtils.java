package com.orrsrosx.utils;

import com.orrsrosx.server.ThreadTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.util.Properties;

/**
 * @author huyun
 * @ClassName 项目配置地址获取
 * @createTime 2021/3/5 17:12
 * @Version
 **/
public class configureUtils {

    private static final Logger log = LoggerFactory.getLogger(ThreadTask.class);

    public static String getConfig(String request) {
        Properties properties = null;
        try {
            properties = PropertiesLoaderUtils.loadAllProperties("config/file.properties");
        } catch (IOException e) {
            log.error("读取错误",e);
        }
        String getPort = properties.getProperty(request);
        if (getPort==null){
            log.info("配置文件错误，请检查.properties配置文件");
        }
        return getPort;
    }
}
