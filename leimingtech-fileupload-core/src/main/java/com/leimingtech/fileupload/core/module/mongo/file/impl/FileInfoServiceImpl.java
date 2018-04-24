package com.leimingtech.fileupload.core.module.mongo.file.impl;

import com.github.pagehelper.PageInfo;
import com.leimingtech.fileupload.core.module.mongo.file.FileInfoService;
import leimingtech.fileupload.entity.file.FileInfoPo;
import leimingtech.fileupload.framework.utils.DateUtils;
import leimingtech.fileupload.framework.utils.IDGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * mongo实现
 *
 * @author panda
 */
@Service
public class FileInfoServiceImpl implements FileInfoService {

    @Autowired
    private MongoTemplate mongoTemplate;


    /**
     * 保存文件信息
     *
     * @param fileInfoPo
     */
    @Override
    public void saveFileInfo(FileInfoPo fileInfoPo) {
        fileInfoPo.setId(IDGenerator.getUUID());
        fileInfoPo.setCreateTime(DateUtils.getNow());
        mongoTemplate.insert(fileInfoPo);
    }


    /**
     * 根据文件的id删除文件信息
     *
     * @param id
     */
    @Override
    public void deleteFileInfo(String id) {
        Criteria criteria = Criteria.where("id").is(id);
        Query query = new Query(criteria);
        mongoTemplate.remove(query, FileInfoPo.class);
    }


    /**
     * 根据文件id获取文件详情
     *
     * @param id
     * @return
     */
    @Override
    public FileInfoPo getFileInfoById(String id) {
        Criteria criteria = Criteria.where("id").is(id);
        Query query = new Query(criteria);
        return mongoTemplate.findOne(query, FileInfoPo.class);
    }


    /**
     * 分页查询文件信息
     *
     * @param fileInfoPo
     * @param pageable    示例：Pageable pageable=new PageRequest(start, 2);
     * @return
     */
    @Override
    public PageInfo<FileInfoPo> searchFileList(FileInfoPo fileInfoPo, Pageable pageable) {
        Query query = new Query();
        List<FileInfoPo> fileInfoPoList = mongoTemplate.findAll(FileInfoPo.class);
        //pageInfo
        PageInfo<FileInfoPo> iamgePageInfo = new PageInfo(fileInfoPoList);
        return iamgePageInfo;
    }
}
