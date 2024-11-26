package com.fleet;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fleet.errors.InvalidDataError;
import com.fleet.errors.MessageResponseError;
import com.fleet.errors.ServerStatusError;

import java.util.Properties;
import java.util.UUID;

public class TruckTrackingProducer {

    public static void main(String[] args) throws Exception {

        // Check if the topic exists, and create it if necessary
        Common.createTopicIfNotExists();

        // Kafka Producer configuration
        Properties properties = new Properties();
        properties.put("bootstrap.servers", Common.BOOTSTRAP_SERVERS);
        properties.put("key.serializer", StringSerializer.class.getName());
        properties.put("value.serializer", StringSerializer.class.getName());

        try (KafkaProducer<String, String> producer = new KafkaProducer<>(properties)) {
        
            TruckTrackingMessage message;

            // try catch the error as InvalidDataError
            try {
                // Create an example message
                message = new TruckTrackingMessage(
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

            } catch (Exception e) {
                producer.close();
                throw new InvalidDataError(e.getMessage());
            }

            

            // Convert message to JSON
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonMessage = objectMapper.writeValueAsString(message);

            // Try catch block to handle exceptions
            try {
                // Send message to Kafka
                ProducerRecord<String, String> record = new ProducerRecord<>(Common.TOPIC, UUID.randomUUID().toString(), jsonMessage);
                producer.send(record);
                
            } catch (Exception e) {
                throw new MessageResponseError(e.getMessage());
            } finally {
                // Close producer
                producer.close();
            }
            
        } catch (Exception e) {
            throw new ServerStatusError(e.getMessage());
        }
    }
}
