package com.leimingtech.fileupload.core.module.receive.fileQueue;

import com.leimingtech.fileupload.core.module.mongo.file.FileInfoService;
import leimingtech.fileupload.entity.file.FileInfoPo;
import leimingtech.fileupload.framework.constant.QueueConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;

@Component
public class FileInfoReceiver {
    /**
     * 请求日志接口
     */
    @Autowired
    FileInfoService fileInfoService;

    /**
     * 文件信息的添加
     *
     * @param message
     * @throws JMSException
     */
    @JmsListener(destination = QueueConstant.Queues.FILE_ADD_QUEUE)
    public void receiver(Message message) throws JMSException {
        ObjectMessage objMsg = (ObjectMessage) message;
        Object obj = objMsg.getObject();
        if (obj instanceof FileInfoPo) {
            fileInfoService.saveFileInfo((FileInfoPo) obj);
        }
    }
}
