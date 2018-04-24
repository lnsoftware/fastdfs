package com.leimingtech.fileupload.api.facade.file;


import com.github.pagehelper.PageInfo;
import com.leimingtech.fileupload.api.dto.ResultDto;
import com.leimingtech.fileupload.api.param.UploadFileParam;
import leimingtech.fileupload.framework.exception.FastdfsException;


/**
 * 文件上传接口
 *
 * @author panda
 */
public interface FileUploadFacade {

    /**
     * 上传文件
     *
     * @param uploadFileDto 上传文件dto
     * @return
     */
    ResultDto uploadFile(UploadFileParam uploadFileDto) throws FastdfsException;

    /**
     * 删除远程文件
     *
     * @param filePath 文件远程地址
     * @return
     */
    void deleteFile(String filePath) throws FastdfsException;


    /**
     * 查看文件列表
     */
    PageInfo lookList(Integer pageNo, Integer pageSize);
}
