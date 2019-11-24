package com.micro.services.product.content.svc.configuration;

import com.micro.services.product.content.svc.service.EventSubscriber;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AMQPConfig {

    @Autowired
    private SubscriberConfig subscriberConfig;

    @Bean
    public TopicExchange receiverExchange() {
        return new TopicExchange("supplierExchange");
    }

    @Bean
    public Queue eventReceivingQueue() {
        if (subscriberConfig.getQueueName() == null) {
            throw new IllegalStateException("No queue to listen to! Please specify the name of the queue to listen to with the property 'subscriber.queue'");
        }
        return new Queue(subscriberConfig.getQueueName());
    }

    @Bean
    public Binding binding(Queue eventReceivingQueue, TopicExchange receiverExchange) {
        if (subscriberConfig.getRoutingKey() == null) {
            throw new IllegalStateException("No events to listen to! Please specify the routing key for the events to listen to with the property 'subscriber.routingKey' (see EventPublisher for available routing keys).");
        }
        return BindingBuilder
                .bind(eventReceivingQueue)
                .to(receiverExchange)
                .with(subscriberConfig.getRoutingKey());
    }

    @Bean
    public SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
                                                    MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(subscriberConfig.getQueueName());
        container.setMessageListener(listenerAdapter);
        return container;
    }

    @Bean
    public MessageListenerAdapter listenerAdapter(EventSubscriber eventSubscriber) {
        return new MessageListenerAdapter(eventSubscriber, "received");
    }

}
