package com.leimingtech.fileupload.service.bootstrap;


import com.leimingtech.fileupload.core.module.fastDfs.impl.FastDfsServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;

@Configuration
@SpringBootApplication(scanBasePackages = {"com.leimingtech.fileupload.service", "com.leimingtech.fileupload.core","leimingtech.fileupload.framework.utils"})
public class FileUploadApplication {

    public static void main(String[] args) {
        SpringApplication.run(FileUploadApplication.class, args);
    }


    /**
     * 添加SpringContextUtil实例注入到Spring容器
     *
     * @return
     */
    @Bean
    public FastDfsServiceImpl FastDfsUtil() {
        FastDfsServiceImpl fastDfsUtil = new FastDfsServiceImpl();
        fastDfsUtil.setMaxPoolSize(maxPoolSize);
        fastDfsUtil.setMinPoolSize(minPoolSize);
        fastDfsUtil.setWaitTimes(waitTimes);
        fastDfsUtil.init();
        return fastDfsUtil;
    }


    @Value("${spring.fastdfs.minPoolSize}")
    private long minPoolSize = 10;

    @Value("${spring.fastdfs.maxPoolSize}")
    private long maxPoolSize = 30;

    @Value("${spring.fastdfs.waitTimes}")
    private long waitTimes = 200;

}
