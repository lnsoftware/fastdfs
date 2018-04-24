package com.leimingtech.fileupload.rest.controller.fileupload.impl;


import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.leimingtech.fileupload.api.dto.ResultDto;
import com.leimingtech.fileupload.api.facade.file.FileUploadFacade;
import com.leimingtech.fileupload.api.param.UploadFileParam;
import com.leimingtech.fileupload.rest.controller.fileupload.FileUploadController;
import org.apache.commons.io.FileUtils;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;


/**
 * 品牌
 *
 * @author uchiha
 * @date 2018/4/4
 */
@RestController
public class FileUploadControllerImpl implements FileUploadController {


    @Reference(version = "1.0.0")
    private FileUploadFacade fileUploadFacade;

    /**
     * 上传文件
     *
     * @param uploadFileParam
     * @return
     */
    @Override
    public ResultDto uploadFile(UploadFileParam uploadFileParam) {
        try {
            //参数校验。。
            uploadFileParam.setFileBytes(FileUtils.readFileToByteArray(new File("/Users/panda/Desktop/panda.jpg")));
            uploadFileParam.setFileExtName(".jpg");
            //上传
            ResultDto resultDto = fileUploadFacade.uploadFile(uploadFileParam);
            //结果返回
            return resultDto;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResultDto();
    }

    /**
     * 删除文件
     *
     * @param id 图片的id
     * @return
     */
    @Override
    public String deleteFile(String id) {
        try {
            fileUploadFacade.deleteFile(id);
        } catch (Exception e) {
            return "fail";
        }
        return "success";
    }


    /**
     * 文件的分页查询
     *
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Override
    public PageInfo lookAllFile(Integer pageNo, Integer pageSize) {
        //查询mongoDB
        PageInfo pageInfo = fileUploadFacade.lookList(pageNo, pageSize);
        return pageInfo;
    }

}
