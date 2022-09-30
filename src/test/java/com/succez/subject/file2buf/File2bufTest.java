package com.orrsrosx.subject.file2buf;

import java.io.File;
import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;
public class File2bufTest {

    @Test
    public void file2bu(){

        // 测试文件正常存在情况
        File f = new File("src/test/java/com/orrsrosx/subject/file2buf/test.txt");
        Assert.assertNotNull(File2buf.file2buf(f));

        // 测试文件存在但内容为空的情况
        File f1 = new File("src/test/java/com/orrsrosx/subject/file2buf/empty.txt");
        String a =new String(File2buf.file2buf(f1));
        Assert.assertEquals("",a);

        // 测试文件不存在的情况
        File f2 = new File("src/test/java/com/orrsrosx/subject/file2buf/null.txt");
        Assert.assertEquals("null", Arrays.toString(File2buf.file2buf(f2)));

        // 测试File对象为null的情况
        File f3 = null;
        Assert.assertEquals("null", Arrays.toString(File2buf.file2buf(f3)));
    }



}