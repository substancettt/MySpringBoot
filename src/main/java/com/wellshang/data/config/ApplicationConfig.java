package com.wellshang.data.config;

import java.io.IOException;

import javax.jms.ConnectionFactory;
import javax.jms.Queue;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.annotation.JmsListenerConfigurer;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerEndpointRegistrar;
import org.springframework.jms.config.SimpleJmsListenerContainerFactory;
import org.springframework.jms.connection.JmsTransactionManager;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableAutoConfiguration
@EnableScheduling
@EnableJms
@ComponentScan(basePackages = { "com.wellshang" })
public class ApplicationConfig {

    @Bean(name = "yamlProperties")
    public PropertySource<?> yamlPropertySourceLoader() throws IOException {
        YamlPropertySourceLoader loader = new YamlPropertySourceLoader();
        PropertySource<?> applicationYamlPropertySource = loader.load("application.yml",
                new ClassPathResource("application.yml"), "default");
        return applicationYamlPropertySource;
    }

    @Bean
    public JmsTransactionManager jmsTransactionManager(ConnectionFactory cf) {
        ActiveMQConnectionFactory amqCf = (ActiveMQConnectionFactory) cf;
        amqCf.setTrustAllPackages(true);
        JmsTransactionManager result = new JmsTransactionManager();
        result.setConnectionFactory(cf);
        return result;
    }
}
