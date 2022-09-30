package com.orrsrosx.filesystem.controller;

import com.orrsrosx.filesystem.service.FileService;
import com.orrsrosx.server.ThreadTask;
import com.orrsrosx.filesystem.entity.*;
import com.orrsrosx.server.entity.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Map;



/**
 * @author huyun
 * @ClassName
 * @createTime 2021/2/1 11:10
 * @Version
 **/
public class FileController {

    private static final Logger log = LoggerFactory.getLogger(ThreadTask.class);


    /**
     * @Description: 获取文件列表
     * @Param:
     * @return:
     * @Author: huyun
     * @Date: 2021/3/5
     */
    public Dir listFile(Map<String, String> param) {
        String path = param.get("path");
        File file = FileService.getFile(path);
        return FileService.getFileList(path, file);
    }
    /**
     * @Description: 删除文件
     * @Param:
     * @return:
     * @Author: huyun
     * @Date: 2021/3/5
     */
    public boolean deletefile(Map<String, String> param) {
        String path = param.get("path");
        File file = FileService.getFile(path);
        return FileService.deleteFile(file);
    }

    /**
     * @Description: 编辑文件
     * @Param:
     * @return: EditVo
     * @Author: huyun
     * @Date: 2021/3/5
     */
    public FileMes editfile(Map<String, String> param) throws IOException {

        String path = param.get("path");
        File file = FileService.getFile(path);
        return new FileService().getEditList(path, file);
    }


    /**
     * @Description: 保存文件
     * @Param:
     * @return: ResponseVo
     * @Author: huyun
     * @Date: 2021/3/5
     */
    public FileMes preserve(Map<String, String> param, String content) throws IOException {
        FileMes responseVo = new FileMes();
        String path = param.get("path");
        File file = FileService.getFile(path);

        if (!file.exists()) {
            responseVo.setCanEdit(false);
        } else if (file.isDirectory()) {
            responseVo.setCanEdit(false);
        }
        //将content以换行分段，然后原文件一行行读取，与分段后的content进行比较不同就进行替换，相同不进行出来，优化程序性能
        else {
            Path pathContent = Path.of(file.getPath());
            String contented = content;
            Files.writeString(pathContent, contented, StandardOpenOption.WRITE);
            responseVo.setCanEdit(true);
        }
        return responseVo;
    }

    /**
     * @Description: 创建文件
     * @Param:
     * @return: ResponseVo
     * @Author: huyun
     * @Date: 2021/3/5
     */
    public FileMes create(Map<String, String> param, String content) throws IOException {
        String[] contents = content.split(":");
        String sign = contents[0];
        String contentFileOrDir = contents[1];
        FileMes responseVo = new FileMes();
        String path = param.get("path");
        File file = FileService.getFile(path);
        //选中文件时同级目录创建
        if (file.getName().contains(".")) {
            path = file.getParent() + "\\";
        }
        if (path == null || path.equals("null")) {
            file = new File(Response.ROOT_PathC + contentFileOrDir);
        } else {
            if (path.contains("D")) {
                file = new File(path + contentFileOrDir);
            } else {
                file = new File(Response.ROOT_Path + path + "\\" + contentFileOrDir);
            }
        }

        if (!file.exists()) {
            if (sign.equals("file")) {
                file.createNewFile();
                log.info("创建文件");
                responseVo.setCanEdit(true);
            } else if (sign.equals("dir")) {
                file.mkdir();
                log.info("创建文件夹");
                responseVo.setCanEdit(true);
            } else {
                responseVo.setCanEdit(false);
                responseVo.setResMessage("创建类型错误");
            }
        } else {
            responseVo.setCanEdit(false);
            responseVo.setResMessage("文件已经存在");
        }
        return responseVo;
    }
}
