package org.fsk.command.handler;

import org.fsk.command.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventPublisherService {

    private final RabbitTemplate rabbitTemplate;

    public void publishEvent(String routingKey, Object event) {
        try {
            rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, routingKey, event);
            log.info("Event published successfully. RoutingKey: {}, Event: {}", routingKey, event);
        } catch (Exception e) {
            log.error("Error publishing event. RoutingKey: {}, Event: {}, Error: {}", 
                    routingKey, event, e.getMessage());
            throw new RuntimeException("Failed to publish event", e);
        }
    }
}
