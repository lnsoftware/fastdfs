package com.leimingtech.fileupload.core.module.fastDfs.impl;

import com.leimingtech.fileupload.core.module.fastDfs.FasfDfsService;
import leimingtech.fileupload.entity.file.FileInfoPo;
import leimingtech.fileupload.framework.constant.ErrorCodes;
import leimingtech.fileupload.framework.constant.LinkType;
import leimingtech.fileupload.framework.exception.FastdfsException;
import leimingtech.fileupload.framework.utils.CommonUtils;
import leimingtech.fileupload.framework.utils.fastDfs.ConnectionPool;
import org.apache.commons.io.FileUtils;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.StorageClient1;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.net.SocketTimeoutException;
import java.util.UUID;

/**
 * FastDfs工具类
 *
 * @author panda
 */
public class FastDfsServiceImpl implements FasfDfsService{
    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(FastDfsServiceImpl.class);
    /**
     * 连接池
     */
    private ConnectionPool connectionPool = null;
    /**
     * 连接池默认最小连接数
     */
    private long minPoolSize = 10;
    /**
     * 连接池默认最大连接数
     */
    private long maxPoolSize = 30;
    /**
     * 当前创建的连接数
     */
    private volatile long nowPoolSize = 0;
    /**
     * 默认等待时间（单位：秒）
     */
    private long waitTimes = 200;

    /**
     * 初始化线程池
     *
     * @Description:
     */
    public void init() {
        String logId = UUID.randomUUID().toString();
        LOGGER.info("[初始化线程池(Init)][" + logId + "][默认参数：minPoolSize="
                + minPoolSize + ",maxPoolSize=" + maxPoolSize + ",waitTimes="
                + waitTimes + "]");
        connectionPool = new ConnectionPool(minPoolSize, maxPoolSize, waitTimes);
    }

    /**
     * @param fileInfoPo 上传文件信息
     * @return
     * @throws FastdfsException
     * @Description: 上传文件
     */
    public FileInfoPo upload(FileInfoPo fileInfoPo) throws FastdfsException {
        String logId = UUID.randomUUID().toString();
        /** 封装文件信息参数 */
        NameValuePair[] metaList = new NameValuePair[]{new NameValuePair("fileName", "")};
        TrackerServer trackerServer = null;
        try {
            /** 获取fastdfs服务器连接 */
            trackerServer = connectionPool.checkout(logId);
            StorageServer storageServer = null;
            StorageClient1 client1 = new StorageClient1(trackerServer, storageServer);

            /** 以文件字节的方式上传 */
            String[] results = client1.upload_file(CommonUtils.getFastdfsGroup(LinkType.OUTERNET, logId), fileInfoPo
                    .getFileBytes(), fileInfoPo.getFileExtName(), metaList);

            /** 上传完毕及时释放连接 */
            connectionPool.checkin(trackerServer, logId);

            LOGGER.info("[上传文件（upload）-fastdfs服务器相应结果][" + logId
                    + "][result：results=" + results + "]");


            /** results[0]:组名，results[1]:远程文件名 */
            if (results != null && results.length == 2) {

                fileInfoPo.setFilePath(CommonUtils.getFileLinkPath(
                        LinkType.OUTERNET, logId)
                        + "/"
                        + results[0]
                        + "/" + results[1]);
                fileInfoPo.setGroupName(results[0]);
                fileInfoPo.setRemoteFilename(results[1]);
            } else {
                /** 文件系统上传返回结果错误 */
                throw ErrorCodes.UPLOAD_RESULT_ERROR.ERROR();
            }
            return fileInfoPo;
        } catch (FastdfsException e) {
            LOGGER.error("[上传文件（upload)][" + logId + "][异常：" + e + "]");
            throw e;

        } catch (SocketTimeoutException e) {
            LOGGER.error("[上传文件（upload)][" + logId + "][异常：" + e + "]");
            throw ErrorCodes.WAIT_IDLECONNECTION_TIMEOUT.ERROR();
        } catch (Exception e) {
            LOGGER.error("[上传文件（upload)][" + logId + "][异常：" + e + "]");
            connectionPool.drop(trackerServer, logId);
            throw ErrorCodes.SYS_ERROR.ERROR();
        }
    }

    /**
     * @param group_name      组名
     * @param remote_filename 远程文件名称
     * @throws FastdfsException
     * @Description: 删除fastdfs服务器中文件
     */
    public void deleteFile(String group_name, String remote_filename)
            throws FastdfsException {
        String logId = UUID.randomUUID().toString();
        LOGGER.info("[ 删除文件（deleteFile）][" + logId + "][parms：group_name="
                + group_name + ",remote_filename=" + remote_filename + "]");
        TrackerServer trackerServer = null;

        try {
            /** 获取可用的tracker,并创建存储server */
            trackerServer = connectionPool.checkout(logId);
            StorageServer storageServer = null;
            StorageClient1 client1 = new StorageClient1(trackerServer,
                    storageServer);
            /** 删除文件,并释放 trackerServer */
            int result = client1.delete_file(group_name, remote_filename);

            /** 上传完毕及时释放连接 */
            connectionPool.checkin(trackerServer, logId);

            LOGGER.info("[ 删除文件（deleteFile）--调用fastdfs客户端返回结果][" + logId
                    + "][results：result=" + result + "]");

            /** 0:文件删除成功，2：文件不存在 ，其它：文件删除出错 */
            if (result == 2) {

                throw ErrorCodes.NOT_EXIST_FILE.ERROR();

            } else if (result != 0) {

                throw ErrorCodes.DELETE_RESULT_ERROR.ERROR();

            }

        } catch (FastdfsException e) {
            LOGGER.error("[ 删除文件（deleteFile）][" + logId + "][异常：" + e + "]");
            throw e;

        } catch (SocketTimeoutException e) {
            LOGGER.error("[ 删除文件（deleteFile）][" + logId + "][异常：" + e + "]");
            throw ErrorCodes.WAIT_IDLECONNECTION_TIMEOUT.ERROR();
        } catch (Exception e) {

            LOGGER.error("[ 删除文件（deleteFile）][" + logId + "][异常：" + e + "]");
            connectionPool.drop(trackerServer, logId);
            throw ErrorCodes.SYS_ERROR.ERROR();

        }
    }

    public ConnectionPool getConnectionPool() {
        return connectionPool;
    }

    public void setConnectionPool(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    public long getMinPoolSize() {
        return minPoolSize;
    }

    public void setMinPoolSize(long minPoolSize) {
        this.minPoolSize = minPoolSize;
    }

    public long getMaxPoolSize() {
        return maxPoolSize;
    }

    public void setMaxPoolSize(long maxPoolSize) {
        this.maxPoolSize = maxPoolSize;
    }

    public long getNowPoolSize() {
        return nowPoolSize;
    }

    public void setNowPoolSize(long nowPoolSize) {
        this.nowPoolSize = nowPoolSize;
    }

    public long getWaitTimes() {
        return waitTimes;
    }

    public void setWaitTimes(long waitTimes) {
        this.waitTimes = waitTimes;
    }


    /**
     * 上传文件的测试类
     *
     * @param args
     * @throws FastdfsException
     */
    public static void main(String[] args) throws FastdfsException {
        /** 日志标识 */
        FastDfsServiceImpl fastDfsUtil = new FastDfsServiceImpl();
        fastDfsUtil.init();
        String logId = UUID.randomUUID().toString();

        FileInfoPo fileInfoPo = new FileInfoPo();
        fileInfoPo.setFileExtName("jpg");
        try {
            fileInfoPo.setFileBytes(FileUtils.readFileToByteArray(new File("/Users/panda/Desktop/panda.jpg")));
        } catch (Exception e) {
            e.printStackTrace();
        }
        LOGGER.info("[上传文件（uploadFile）][" + logId + "][prams：FileInfo=" + fileInfoPo.toString() + "]");
        try {
            /** 调用工具类上传 */
            FileInfoPo result = fastDfsUtil.upload(fileInfoPo);

            System.out.println(result.getFilePath());
            System.out.println(result.getGroupName());
            System.out.println(result.getRemoteFilename());

            LOGGER.info("[上传文件（uploadFile)-返回结果][" + logId
                    + "][results：resultDto=" + result.toString() + "]");
        } catch (FastdfsException e) {
            LOGGER.error("[上传文件（uploadFile)][" + logId + "][异常：" + e + "]");
            throw e;
        }
    }
}
