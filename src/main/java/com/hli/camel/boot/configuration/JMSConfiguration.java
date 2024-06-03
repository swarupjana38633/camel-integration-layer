package com.hli.camel.boot.configuration;

import jakarta.jms.ConnectionFactory;
import org.apache.camel.component.jms.JmsComponent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.connection.JmsTransactionManager;

@Configuration
public class JMSConfiguration {

    @Bean
    public JmsTransactionManager jmsTransactionManager(ConnectionFactory connectionFactory) {
        return new JmsTransactionManager(connectionFactory);
    }

    @Bean
    public JmsComponent jmsComponent(ConnectionFactory connectionFactory, JmsTransactionManager jmsTransactionManager) {
        return JmsComponent.jmsComponentTransacted(connectionFactory, jmsTransactionManager);
    }
}
