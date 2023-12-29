package com.morello.message_producer_assignment_2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class MessageProducerAssignment2Application {

    public static void main(String[] args) {
        SpringApplication.run(MessageProducerAssignment2Application.class, args);
    }

}
