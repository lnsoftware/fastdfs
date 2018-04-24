package com.leimingtech.fileupload.rest.controller.fileupload;


import com.github.pagehelper.PageInfo;
import com.leimingtech.fileupload.api.dto.ResultDto;
import com.leimingtech.fileupload.api.param.UploadFileParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author panda
 * @date 2018/4/4
 */
@RequestMapping("api/file")
public interface FileUploadController {
    /**
     * 上传图片
     *
     * @param uploadFileParam
     * @return
     */
    @GetMapping("upload")
    ResultDto uploadFile(UploadFileParam uploadFileParam);

    /**
     * @param id 图片的id
     * @return
     * @throws Exception
     */
    @GetMapping("delete")
    String deleteFile(String id);


    /**
     * 文件的查看
     *
     * @param
     * @return
     * @throws Exception
     */
    @GetMapping("look")
    PageInfo lookAllFile(Integer pageNo, Integer pageSize);


}
