package org.fsk.command.config;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE_NAME = "cqrs.exchange";
    public static final String PRODUCT_QUEUE = "product.queue";
    public static final String ORDER_QUEUE = "order.queue";
    public static final String CUSTOMER_QUEUE = "customer.queue";
    public static final String SHIPMENT_QUEUE = "shipment.queue";

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(EXCHANGE_NAME);
    }

    @Bean
    public Queue productQueue() {
        return new Queue(PRODUCT_QUEUE, true);
    }

    @Bean
    public Queue orderQueue() {
        return new Queue(ORDER_QUEUE, true);
    }

    @Bean
    public Queue customerQueue() {
        return new Queue(CUSTOMER_QUEUE, true);
    }

    @Bean
    public Queue shipmentQueue() {
        return new Queue(SHIPMENT_QUEUE, true);
    }

    @Bean
    public Binding productBinding(Queue productQueue, DirectExchange exchange) {
        return BindingBuilder
                .bind(productQueue)
                .to(exchange)
                .with("product.routing.key");
    }

    @Bean
    public Binding orderBinding(Queue orderQueue, DirectExchange exchange) {
        return BindingBuilder
                .bind(orderQueue)
                .to(exchange)
                .with("order.routing.key");
    }

    @Bean
    public Binding customerBinding(Queue customerQueue, DirectExchange exchange) {
        return BindingBuilder
                .bind(customerQueue)
                .to(exchange)
                .with("customer.routing.key");
    }

    @Bean
    public Binding shipmentBinding(Queue shipmentQueue, DirectExchange exchange) {
        return BindingBuilder
                .bind(shipmentQueue)
                .to(exchange)
                .with("shipment.routing.key");
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter());
        return rabbitTemplate;
    }
}
