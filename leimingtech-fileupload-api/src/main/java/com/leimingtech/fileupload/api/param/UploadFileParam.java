package com.leimingtech.fileupload.api.param;


import leimingtech.fileupload.framework.constant.LinkType;
import lombok.Data;

import java.io.Serializable;


/**
 * 文件上传信息
 *
 * @author panda
 */
@Data
public class UploadFileParam implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 文件扩展名
     */
    private String fileExtName;
    /**
     * 文件访问类型（内网、外网；为空时为内网访问 ）
     */
    private LinkType linkType;
    /**
     * 文件字节形式
     */
    private byte[] fileBytes;

    /**
     * 带参构造函数
     *
     * @param fileExtName 文件扩展名（如：jpg、xls、text）
     * @param linkType    文件访问类型
     * @param fileBytes   文件字节
     */
    public UploadFileParam(String fileExtName, LinkType linkType, byte[] fileBytes) {
        super();
        this.fileExtName = fileExtName;
        this.linkType = linkType;
        this.fileBytes = fileBytes;
    }

    public UploadFileParam(){}


}
