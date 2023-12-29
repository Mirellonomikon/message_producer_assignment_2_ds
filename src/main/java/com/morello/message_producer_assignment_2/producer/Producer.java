package com.morello.message_producer_assignment_2.producer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

@Component
public class Producer {

    private static final String queue_name = "energy_data_queue";

    public List<String[]> readCSV(String filepath) throws IOException, CsvException {
        FileReader filereader = new FileReader(filepath);
        CSVReader csvReader = new CSVReader(filereader);
        return csvReader.readAll();
    }

    @Bean
    public void messageProducer() throws URISyntaxException, NoSuchAlgorithmException, KeyManagementException, IOException, TimeoutException, CsvException, InterruptedException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setUri("amqps://egsbshkj:NjCKxQzILzeK8TWRtK1rZpsCf4nq4Ndp@goose.rmq2.cloudamqp.com/egsbshkj");
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(queue_name, false, false, false, null);
        List<String[]> csvData = readCSV("sensor.csv");
        ObjectMapper objectMapper = new ObjectMapper();

        Scanner scanner = new Scanner(new File("device.cfg"));
        Integer deviceId = Integer.parseInt(scanner.nextLine());
        scanner.close();


        for (String[] data : csvData) {
            String message = objectMapper.writeValueAsString(
                    new Object() {
                        public long timestamp = System.currentTimeMillis();
                        public Integer device_id = deviceId;
                        public double measurement_value = Double.parseDouble(data[0]);
                    });
            channel.basicPublish("", queue_name, null, message.getBytes());
            System.out.println("Sent '" + message + "'");
            Thread.sleep(10000);

        }
    }
}
