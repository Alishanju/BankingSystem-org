package com.alisha.customerservice.service;

import com.alisha.customerservice.config.RabbitMQConfig;
import com.alisha.customerservice.event.CustomerCreatedEvent;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.slf4j.MDC;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class RabbitMQPublisher {

        private final RabbitTemplate rabbitTemplate;

        public void publishCustomerCreated(
                        CustomerCreatedEvent event) {

                // rabbitTemplate.convertAndSend(
                // RabbitMQConfig.CUSTOMER_CREATED_QUEUE,
                // event);
                rabbitTemplate.convertAndSend(
                                RabbitMQConfig.CUSTOMER_CREATED_QUEUE,
                                event,
                                message -> {

                                        message.getMessageProperties()
                                                        .setHeader(
                                                                        "traceId",
                                                                        MDC.get("traceId"));

                                        return message;

                                        
                                });

        


                log.info(
                                "Published customer created event for customer id {}",
                                event.getCustomerId());
        }
}