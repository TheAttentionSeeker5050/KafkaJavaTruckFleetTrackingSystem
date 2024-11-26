package com.fleet;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.CreateTopicsResult;
import org.apache.kafka.clients.admin.ListTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Properties;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class TruckTrackingProducer {
    // private static final String TOPIC = "travel-log";
    // private static final String BOOTSTRAP_SERVERS = "localhost:9091";

    public static void main(String[] args) throws Exception {

        // Check if the topic exists, and create it if necessary
        Common.createTopicIfNotExists();

        // Kafka Producer configuration
        Properties properties = new Properties();
        properties.put("bootstrap.servers", Common.BOOTSTRAP_SERVERS);
        properties.put("key.serializer", StringSerializer.class.getName());
        properties.put("value.serializer", StringSerializer.class.getName());
        
        

        KafkaProducer<String, String> producer = new KafkaProducer<>(properties);

        System.out.println("Starting producer...");

        // Create an example message
        TruckTrackingMessage message = new TruckTrackingMessage(
            "T12345", 
            "Refrigerated", 
            "Refrigerated Goods", 
            true, 
            15000, 
            "In Transit", 
            "Halifax, NS", 
            "Toronto, ON", 
            "Moncton, NB", 
            100, 
            6.5, 
            "2024-11-26T14:00:00Z"
        );

        // Convert message to JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonMessage = objectMapper.writeValueAsString(message);

        // Send message to Kafka
        ProducerRecord<String, String> record = new ProducerRecord<>(Common.TOPIC, UUID.randomUUID().toString(), jsonMessage);
        producer.send(record);
        System.out.println("Message sent: " + jsonMessage);

        // Close producer
        producer.close();

        
    }
}
