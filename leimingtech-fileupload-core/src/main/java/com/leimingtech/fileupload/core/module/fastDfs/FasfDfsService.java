package com.leimingtech.fileupload.core.module.fastDfs;

import leimingtech.fileupload.entity.file.FileInfoPo;
import leimingtech.fileupload.framework.exception.FastdfsException;

/**
 * FastDfs服务
 */
public interface FasfDfsService {

    /**
     * 文件上传
     *
     * @param fileInfoPo 上传文件信息
     * @return
     * @throws FastdfsException
     */
    FileInfoPo upload(FileInfoPo fileInfoPo) throws FastdfsException;


    /**
     * 文件的删除
     *
     * @param group_name
     * @param remote_filename
     * @throws FastdfsException
     */
    void deleteFile(String group_name, String remote_filename) throws FastdfsException;


}
