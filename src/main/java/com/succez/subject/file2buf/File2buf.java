package com.orrsrosx.subject.file2buf;

import java.io.*;

/**
 *
 * @author huyun
 * @ClassName IntToHex
 * @createTime 2021/1/26 14:14
 * @Version
 */
public class File2buf {
    /**
     * 将文件内容转换成byte数组返回,如果文件不存在或者读入错误返回null
     */
    public static byte[] file2buf(File fob){
            if (fob == null)
            {
                return null;
            }
            //不存在的情况
            if (!fob.exists())
            {
                return null;
            }


            byte[] buffer = null;
        try{
            FileInputStream fis = new FileInputStream(fob);//字节输入流

            //字节数组输出流在内存中创建一个字节数组缓冲区
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] b = new byte[1024];

            int len=-1;
            while ((len = fis.read(b)) != -1)
            {
                bos.write(b, 0, len);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return buffer;
    }

}
