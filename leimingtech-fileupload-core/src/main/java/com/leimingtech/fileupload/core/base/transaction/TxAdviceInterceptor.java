package com.leimingtech.fileupload.core.base.transaction;

import org.springframework.aop.framework.autoproxy.BeanNameAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import java.util.Properties;

/**
 * 事务管理
 */
@Configuration
public class TxAdviceInterceptor {

    @Autowired
    private DataSourceTransactionManager transactionManager;

    @Bean(name = "txAdvice")
    public TransactionInterceptor getAdvisor() {
        Properties properties = new Properties();
        properties.setProperty("add*", "PROPAGATION_REQUIRED");
        properties.setProperty("insert*", "PROPAGATION_REQUIRED");
        properties.setProperty("save*", "PROPAGATION_REQUIRED");
        properties.setProperty("update*", "PROPAGATION_REQUIRED");
        properties.setProperty("modify*", "PROPAGATION_REQUIRED");
        properties.setProperty("delete*", "PROPAGATION_REQUIRED");
        properties.setProperty("apply*", "PROPAGATION_REQUIRED");
        properties.setProperty("attach*", "PROPAGATION_REQUIRED");
        properties.setProperty("change*", "PROPAGATION_REQUIRED");
        properties.setProperty("get*", "PROPAGATION_NOT_SUPPORTED,readOnly");
        properties.setProperty("load*", "PROPAGATION_NOT_SUPPORTED,readOnly");
        properties.setProperty("search*", "PROPAGATION_NOT_SUPPORTED,readOnly");
        properties.setProperty("query*", "PROPAGATION_NOT_SUPPORTED,readOnly");
        properties.setProperty("*", "PROPAGATION_REQUIRED,readOnly");
        TransactionInterceptor tsi = new TransactionInterceptor(transactionManager, properties);
        return tsi;
    }

    @Bean
    public BeanNameAutoProxyCreator txProxy() {
        BeanNameAutoProxyCreator creator = new BeanNameAutoProxyCreator();
        creator.setInterceptorNames("txAdvice");
        creator.setBeanNames("*Service", "*ServiceImpl");
        creator.setProxyTargetClass(true);
        return creator;
    }
}
