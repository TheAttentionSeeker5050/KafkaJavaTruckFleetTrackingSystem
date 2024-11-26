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

        // // Set number of brokers to 1
        // properties.put("acks", "1");
        // properties.put("retries", "1");
        // properties.put("batch.size", 16384);

        // properties.put("metadata.fetch.timeout.ms", "5000");

        
        

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

    // private static void createTopicIfNotExists() {
    //     Properties adminProps = new Properties();
    //     adminProps.put("bootstrap.servers", Common.BOOTSTRAP_SERVERS);
    //     AdminClient adminClient = AdminClient.create(adminProps);

    //     try {
    //         ListTopicsResult topics = adminClient.listTopics();
    //         Set<String> topicNames = topics.names().get();

    //         // Check if the topic exists
    //         if (!topicNames.contains(Common.TOPIC)) {
    //             // If not, create the topic with 1 partition and replication factor of 1
    //             NewTopic newTopic = new NewTopic(Common.TOPIC, 1, (short) 1);  // 1 partition, replication factor 1
    //             CreateTopicsResult result = adminClient.createTopics(java.util.Collections.singletonList(newTopic));
    //             result.all().get();  // Wait until the topic is created
    //             System.out.println("Topic created: " + Common.TOPIC + " with replication factor 1");
    //         } else {
    //             System.out.println("Topic " + Common.TOPIC + " already exists.");
    //         }
    //     } catch (ExecutionException | InterruptedException e) {
    //         System.out.println("Error creating topic: " + e.getMessage());
    //     } finally {
    //         adminClient.close();
    //     }
    // }
}
