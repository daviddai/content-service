package com.micro.services.product.content.svc.configuration;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.ReflectionUtils.MethodCallback;

@Component
public class EventSubscriberAnnotationProcessor implements BeanPostProcessor {

    @Autowired
    private RabbitListenerContainerFactory<SimpleMessageListenerContainer> rabbitListenerContainerFactory;

    @Autowired
    private AmqpAdmin amqpAdmin;

    @Autowired
    private ConnectionFactory connectionFactory;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Class<?> managedBeanClass = bean.getClass();
        MethodCallback methodCallback = new EventSubscriberMethodCallback(amqpAdmin, connectionFactory, rabbitListenerContainerFactory, bean);
        ReflectionUtils.doWithMethods(managedBeanClass, methodCallback);
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
