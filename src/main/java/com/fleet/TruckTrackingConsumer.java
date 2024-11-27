package com.fleet;

import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fleet.common.ServerInterface;
import com.fleet.errors.BaseTruckTrackingError;
import com.fleet.errors.MessageResponseError;
import com.fleet.errors.ServerStatusError;
import com.fleet.models.TruckTrackingMessage;

import java.util.Properties;
import java.time.Duration;
import java.util.Collections;

public class TruckTrackingConsumer {

    public KafkaConsumer<String, String> consumer;
    public Properties properties;

    // Make a data structure that the consumer will use to modify the list of topics it is subscribed to
    public ConsumerRecords<String, String> records;

    public TruckTrackingConsumer() {
        // Kafka Consumer configuration
        this.properties = new Properties();
        this.properties.put("bootstrap.servers", ServerInterface.BOOTSTRAP_SERVERS);
        this.properties.put("group.id", ServerInterface.GROUP_ID);
        this.properties.put("key.deserializer", StringDeserializer.class.getName());
        this.properties.put("value.deserializer", StringDeserializer.class.getName());
        this.properties.put("auto.offset.reset", "earliest");
    }

    public void subscribeToTopic() throws BaseTruckTrackingError {

        try {
            // Create a KafkaConsumer instance
            this.consumer = new KafkaConsumer<>(this.properties);

            // Subscribe to the topic
            this.consumer.subscribe(Collections.singletonList(ServerInterface.TOPIC));

            // Poll for new messages
            while (true) {
                this.records = this.consumer.poll(Duration.ofMillis(100));
                for (ConsumerRecord<String, String> record : this.records) {
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
                        // e.printStackTrace();
                        throw new MessageResponseError(e.getMessage());
                    }
                }
            }
        } catch (Exception e) {
            throw new ServerStatusError(e.getMessage());
        }
    }
}
