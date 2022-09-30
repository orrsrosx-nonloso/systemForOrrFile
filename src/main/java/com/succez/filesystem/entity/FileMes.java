package com.orrsrosx.filesystem.entity;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;


/**
 * @author huyun
 * @ClassName filevo
 * @createTime 2021/1/28 14:49
 * @Version
 **/
@Data
public class FileMes {

    /**
     * 修改日期
     */
    @JsonProperty("lastModifiedTime")
    private String lastChangeDate;

    /**
     * 文件大小
     */
    @JsonProperty("fileSize")
    private long fileSize;

    /**
     * 文件名称
     */
    @JsonProperty("fileName")
    private String fileName;

    /**
     * 文件内容
     */
    @JsonProperty("fileContent")
    private  String fileContent;


    /**
     * 返回信息
     */
    @JsonProperty("resMessage")
    private String resMessage;

    /**
     * 返回数据
     */
    @JsonProperty("resData")
    private String data;


    /**
     * 文件是否可以编辑
     */
    @JsonProperty("fileCanEdit")
    private  boolean canEdit;

    public FileMes(String fileName, String fileContent, boolean canEdit) {
        this.fileName = fileName;
        this.fileContent = fileContent;
        this.canEdit = canEdit;
    }

    public FileMes() {

    }
}
