package com.leimingtech.fileupload.core.module.mongo.file;

import com.github.pagehelper.PageInfo;
import leimingtech.fileupload.entity.file.FileInfoPo;
import org.springframework.data.domain.Pageable;

/**
 * mongo接口
 *
 * @author panda
 */
public interface FileInfoService {

    /**
     * 保存文件信息
     *
     * @param FileInfoPo
     */
    void saveFileInfo(FileInfoPo FileInfoPo);


    /**
     * 删除文件信息
     *
     * @param id
     */
    void deleteFileInfo(String id);

    /**
     * 获取图片信息
     *
     * @param id
     * @return
     */
    FileInfoPo getFileInfoById(String id);

    /**
     * 分页查询
     *
     * @param FileInfoPo
     * @param pageable    示例：Pageable pageable=new PageRequest(start, 2);
     * @return
     */
    PageInfo<FileInfoPo> searchFileList(FileInfoPo FileInfoPo, Pageable pageable);

}
