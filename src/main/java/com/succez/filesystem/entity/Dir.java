package com.orrsrosx.filesystem.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * @author huyun
 * @ClassName DirVo
 * @createTime 2021/2/1 16:10
 * @Version
 **/
@Data
public class Dir {
    /**
     * 目录名称
     */
    @JsonProperty("dirName")
    private String dirName;

    /**
     * 目录路径
     */
    @JsonProperty("dirPath")
    private String dirPath;

    /**
     * 子目录集合
     */
    @JsonProperty("directories")
    private List<Dir> directories;

    /**
     * 目录下文件的集合
     */
    @JsonProperty("fileList")
    private List<FileMes> fileVos;

    /**
     * 最后被修改时间
     */
    @JsonProperty("lastModifiedTime")
    private String lastModifiedTime;

    /**
     * 文件夹存在性
     */
    @JsonProperty("existence")
    private boolean existence;

}
