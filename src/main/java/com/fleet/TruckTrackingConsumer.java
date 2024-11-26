package com.fleet;

import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Properties;
import java.time.Duration;
import java.util.Collections;

public class TruckTrackingConsumer {

    public static void main(String[] args) throws Exception {
        // Kafka Consumer configuration
        Properties properties = new Properties();
        properties.put("bootstrap.servers", Common.BOOTSTRAP_SERVERS);
        properties.put("group.id", Common.GROUP_ID);
        properties.put("key.deserializer", StringDeserializer.class.getName());
        properties.put("value.deserializer", StringDeserializer.class.getName());
        properties.put("auto.offset.reset", "earliest");

        System.out.println("Starting consumer...");

        try (KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties)) {
            // Subscribe to the topic
            consumer.subscribe(Collections.singletonList(Common.TOPIC));

            // Poll for new messages
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
                for (ConsumerRecord<String, String> record : records) {
                    try {
                        // Deserialize JSON into a Java object
                        ObjectMapper objectMapper = new ObjectMapper();
                        TruckTrackingMessage message = objectMapper.readValue(record.value(), TruckTrackingMessage.class);

                        // Print the received message
                        System.out.println("Received message: " +
                            message.getTruckId() + " is " + 
                            message.getStatus() + " at " + 
                            message.getCurrentLocation());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
