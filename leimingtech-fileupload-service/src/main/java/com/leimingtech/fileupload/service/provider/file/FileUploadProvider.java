package com.leimingtech.fileupload.service.provider.file;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageInfo;
import com.leimingtech.fileupload.api.dto.ResultDto;
import com.leimingtech.fileupload.api.facade.file.FileUploadFacade;
import com.leimingtech.fileupload.api.param.UploadFileParam;
import com.leimingtech.fileupload.core.module.fastDfs.FasfDfsService;
import com.leimingtech.fileupload.core.module.mongo.file.FileInfoService;
import leimingtech.fileupload.entity.file.FileInfoPo;
import leimingtech.fileupload.framework.constant.ErrorCodes;
import leimingtech.fileupload.framework.constant.QueueConstant;
import leimingtech.fileupload.framework.exception.FastdfsException;
import leimingtech.fileupload.framework.utils.CommonUtils;
import leimingtech.fileupload.framework.utils.IDGenerator;
import com.leimingtech.fileupload.core.mq.MqService;
import leimingtech.fileupload.framework.utils.converter.BeanConverterProvider;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.jms.core.JmsTemplate;


/**
 * 文件管理
 *
 * @author panda
 */
@Service(version = "1.0.0")
@Slf4j
public class FileUploadProvider implements FileUploadFacade {

    /**
     * fastdfs
     */
    @Autowired
    private FasfDfsService fastDfsService;

    /**
     * 文件基本信息
     */
    @Autowired
    private FileInfoService fileInfoService;

    @Autowired
    JmsTemplate jmsTemplate;


    @Autowired
    MqService mqService;

    /**
     * 文件的上传
     *
     * @param uploadFileParam 上传文件dto
     * @return
     * @throws FastdfsException
     */
    @Override
    public ResultDto uploadFile(UploadFileParam uploadFileParam) throws FastdfsException {
        //判断参数是否为空
        vlidParamIsNull(uploadFileParam);
        FileInfoPo fileInfoPo = BeanConverterProvider.doConvert(uploadFileParam, FileInfoPo.class);
        //调用工具类上传
        FileInfoPo result = fastDfsService.upload(fileInfoPo);
        result.setFileBytes(null);
        //存储文件信息
        mqService.send(QueueConstant.Queues.FILE_ADD_QUEUE, result);
        //返回结果信息
        return BeanConverterProvider.doConvert(result, ResultDto.class);
    }


    /**
     * 查看文件列表
     *
     * @param pageNo
     * @param pageSize
     */
    @Override
    public PageInfo<FileInfoPo> lookList(Integer pageNo, Integer pageSize) {
        Pageable pageable=new PageRequest(pageNo, pageSize);
        PageInfo<FileInfoPo> fileInfoPoPageInfo = fileInfoService.searchFileList(new FileInfoPo(), pageable);
        return fileInfoPoPageInfo;
    }

    /**
     * 文件的删除
     *
     * @param filePath 文件远程地址
     * @throws FastdfsException
     */
    @Override
    public void deleteFile(String filePath) throws FastdfsException {
        try {
            if (StringUtils.isBlank(filePath)) {
                throw ErrorCodes.PARAMETER_IS_NULL.ERROR();
            }
            //解析文件路径
            String[] results = CommonUtils.parseFilePath(filePath, IDGenerator.getUUID());
            //删除文件
            fastDfsService.deleteFile(results[0], results[1]);
            //删除图片信息
            fileInfoService.deleteFileInfo(filePath);
        } catch (FastdfsException e) {
            log.error("[ 删除文件（deleteFile）][异常：" + e + "]");
            throw e;

        }
    }


    /**
     * @param uploadFileDto
     * @Description: 验证上传方法参数是否为空
     */
    private void vlidParamIsNull(UploadFileParam uploadFileDto)
            throws FastdfsException {
        if (null == uploadFileDto || uploadFileDto.getFileBytes() == null
                || null == uploadFileDto.getFileExtName()) {
            throw ErrorCodes.PARAMETER_IS_NULL.ERROR();
        }

    }
}
