package com.micro.services.product.content.svc.configuration;

import com.micro.services.product.content.svc.event.EventSubscriber;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerEndpoint;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistry;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.UUID;

public class EventSubscriberMethodCallback implements ReflectionUtils.MethodCallback {

    private RabbitListenerContainerFactory<SimpleMessageListenerContainer> rabbitListenerContainerFactory;
    private AmqpAdmin amqpAdmin;
    private ConnectionFactory connectionFactory;
    private Object bean;

    public EventSubscriberMethodCallback(AmqpAdmin amqpAdmin,
                                         ConnectionFactory connectionFactory,
                                         RabbitListenerContainerFactory<SimpleMessageListenerContainer> rabbitListenerContainerFactory,
                                         Object bean) {
        this.amqpAdmin = amqpAdmin;
        this.connectionFactory = connectionFactory;
        this.rabbitListenerContainerFactory = rabbitListenerContainerFactory;
        this.bean = bean;
    }

    @Override
    public void doWith(Method method) throws IllegalArgumentException {
        if (method.isAnnotationPresent(EventSubscriber.class)) {
            MessageListenerAdapter messageListenerAdapter = new MessageListenerAdapter(bean, method.getName());
            SimpleMessageListenerContainer messageListenerContainer = new SimpleMessageListenerContainer();
            messageListenerContainer.setConnectionFactory(connectionFactory);
            messageListenerContainer.setQueueNames("product-content");
            messageListenerContainer.setMessageListener(messageListenerAdapter);

            SimpleRabbitListenerEndpoint simpleRabbitListenerEndpoint = new SimpleRabbitListenerEndpoint();
            simpleRabbitListenerEndpoint.setMessageListener(messageListenerAdapter);
            simpleRabbitListenerEndpoint.setId(UUID.randomUUID().toString());

            TopicExchange exchange = new TopicExchange("supplierExchange");
            Queue queue = new Queue("product-content");

            amqpAdmin.declareExchange(exchange);
            amqpAdmin.declareQueue(queue);

            amqpAdmin.declareBinding(BindingBuilder
                    .bind(queue)
                    .to(exchange)
                    .with("supplier.createProduct"));

            rabbitListenerContainerFactory.createListenerContainer(simpleRabbitListenerEndpoint);
        }
    }
}
