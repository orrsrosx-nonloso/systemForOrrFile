package com.orrsrosx.filesystem.service;

import com.orrsrosx.server.ThreadTask;
import com.orrsrosx.filesystem.entity.Dir;
import com.orrsrosx.filesystem.entity.FileMes;
import com.orrsrosx.server.entity.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.activation.MimetypesFileTypeMap;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * @author huyun
 * @ClassName 文件类型处理
 * @createTime 2021/1/29 11:19
 * @Version
 **/
public class FileService {
    private static final Logger log = LoggerFactory.getLogger(ThreadTask.class);

    public static final List<String> getFileType = Arrays.asList("ada", "ads", "as", "ascx", "asp", "bat", "c", "cmd", "conf", "cpp", "cpt", "css", "csv",
            "gitignore", "go", "groovy", "h", "htm", "html", "info", "java", "js", "json", "jsp", "less", "log", "markdown", "md", "mk",
            "pg", "php", "properties", "py", "scss", "sql", "text", "textile", "ts", "txt", "vue", "webapp", "xml", "yaml", "yml");
    public static final List<String> imgType = Arrays.asList("jpg","png","gif","tif","bmp","psd","ico","bpg","jp2", "jpm","jpx"
    );


    /**
     * @Description: 获取文件type
     * @Param:
     * @return:String
     * @Author: huyun
     * @Date: 2021/3/22
     */
    public String getContentType(String URL) {
        //利用nio提供的类判断文件ContentType
        Path path = Paths.get(URL);

        String content_type = null;
        try {
            content_type = java.nio.file.Files.probeContentType(path);
        } catch (IOException e) {
            log.error("Read File ContentType Error");
        }
        //若失败则调用另一个方法进行判断
        java.io.File file = new java.io.File(Response.ROOT_Path + URL);
        if (content_type == null) {
            content_type = new MimetypesFileTypeMap().getContentType(file);
        }
        return content_type;

    }


    /**
     * @Description: 获取文件列表
     * @Param: String path, File file
     * @return: DirVo
     * @Author: huyun
     * @Date: 2021/3/22
     */
    public static Dir getFileList(String path, java.io.File file) {
        Dir dirvo = new Dir();
        if(file.exists()) {
            java.io.File[] files = file.listFiles();
            if (files == null) {
                throw new IllegalArgumentException("err with path: " + file);
            }
            dirvo.setDirName(file.getName());

            Date dateList = new Date((file.lastModified()));
            String date_Str = dateToString(dateList);
            dirvo.setLastModifiedTime(date_Str);

            dirvo.setDirPath(Objects.requireNonNullElse(path, Response.PRO_PATH));

            List<Dir> dirvoList = new ArrayList<>();

            List<FileMes> fileList = new ArrayList<>();

            for (java.io.File fileArr : files) {
                // 如果是目录
                if (fileArr.isDirectory()) {
                    Dir dirVo = new Dir();
                    dirVo.setDirName(fileArr.getName());
                    Date date = new Date((fileArr.lastModified()));
                    String date_String = dateToString(date);
                    dirVo.setLastModifiedTime(date_String);
                    dirvoList.add(dirVo);
                }
                // 如果是文件
                else {
                    FileMes fileVo = new FileMes();
                    fileVo.setFileName(fileArr.getName());
                    fileVo.setFileSize(Long.parseLong(dateProcessing(fileArr.length(), 1024)));
                    Date date = new Date((fileArr.lastModified()));
                    String date_String = dateToString(date);
                    fileVo.setLastChangeDate(date_String);
                    fileList.add(fileVo);
                }
            }
            dirvo.setDirectories(dirvoList);
            dirvo.setFileVos(fileList);
            dirvo.setExistence(true);
            return dirvo;
        }
        else {
            dirvo.setExistence(false);
            return dirvo;
        }
    }

    /**
     * @Description: 获取文件
     * @Param: string
     * @return: File
     * @Author: huyun
     * @Date: 2021/3/22
     */
    public static java.io.File getFile(String path) {
        if (null == path || path.equals("null")) {
            return new java.io.File(Response.ROOT_P);
        } else {
            int i = Response.PRO_PATH.length();
            String s = path.substring(i);
            return new java.io.File(Response.ROOT_P + s);
        }
    }

    /**
    * @Description: 删除文件工具
    * @Param: File
    * @return: boolean
    * @Author: huyun
    * @Date: 2021/3/22
    */
    public static boolean deleteFile(java.io.File file) {
        if (file != null && file.exists()) {
            if (file.isFile()) {
                file.delete();
            } else if (file.isDirectory()) {
                java.io.File[] files = file.listFiles();
                for (int i = 0; i < Objects.requireNonNull(files).length; i++) {
                    deleteFile(files[i]);
                }
                file.delete();
            }
            return true;
        } else
            return false;
    }

    /**
     * @Description:获取文件编辑列表工具类
     * @Param: sd
     * @return:
     * @Author: huyun
     * @Date: 2021/2/5
     */
    public FileMes getEditList(String path, File file) throws IOException {
        if (file.exists()) {
            if (path.contains(".")) {
                String[] types = path.split("\\.");
                int length = types.length - 1;
                String fileType = types[length];
                if (getFileType.contains(fileType)) {
                    String fileStr = Files.readString(file.toPath());
                    log.info("内容导入成功");
                    return new FileMes(file.getName(), fileStr, true);
                } else if (imgType.contains(fileType)) {
                    return new FileMes(file.getName(), path, true);
                } else {
                    return new FileMes(file.getName(), null, false);
                }
            } else {
                return new FileMes(file.getName(), null, false);
            }
        } else {
            return new FileMes(file.getName(), "文件不存在！", false);
        }
    }


    public static String dateProcessing(long member, int denominator) {
        long num = member / denominator + 1;
        DecimalFormat df = new DecimalFormat("0");
        return df.format(num);
    }

    //TODO 时间格式获取
    public static String dateToString(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm");
        return sdf.format(date);
    }
}
