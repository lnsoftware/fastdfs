package com.leimingtech.fileupload.api.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 类 名: ResultDto
 * 描 述: 上传文件返回结果dto
 * 作 者: 张桐
 * 创 建：2018/3/29 上午10:36
 * 版 本：v1.0.0
 * <p>
 * 历 史: (版本) 作者 时间 注释
 */
@Data
public class ResultDto implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 文件访问路径
     */
    private String filePath;
    /**
     * 文件所在组名
     */
    private String groupName;
    /**
     * 文件远程文件名
     */
    private String remoteFilename;

    /**
     * 有参构造函数
     *
     * @param filePath       文件路径
     * @param groupName      组名
     * @param remoteFilename 远程文件名称
     */
    public ResultDto(String filePath, String groupName, String remoteFilename) {
        super();
        this.filePath = filePath;
        this.groupName = groupName;
        this.remoteFilename = remoteFilename;
    }

    public ResultDto() {
    }


}
