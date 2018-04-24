package com.leimingtech.fileupload.core.mq;

import leimingtech.fileupload.framework.utils.SpringContextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import java.io.Serializable;

@Component
public class MqService {


    @Bean
    public MqService get() {
        return new MqService();
    }

    @Autowired
    private JmsTemplate jmsTemplate;

    /**
     * 发送一条消息到指定的队列（目标）
     *
     * @param queueName 队列名称
     * @param message   消息内容
     */
    public void send(String queueName, final String message) {
        jmsTemplate.send(queueName, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage(message);
            }
        });
    }

    /**
     * 发送对象消息到指定队列
     *
     * @param queueName 队列名称
     * @param obj       消息内容
     */
    public void send(String queueName, final Serializable obj) {
        jmsTemplate.send(queueName, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return session.createObjectMessage(obj);
            }
        });
    }

}
