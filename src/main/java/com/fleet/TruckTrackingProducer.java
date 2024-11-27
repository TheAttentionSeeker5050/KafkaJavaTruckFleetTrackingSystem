package com.fleet;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fleet.common.ServerInterface;
import com.fleet.errors.BaseTruckTrackingError;
import com.fleet.errors.MessageResponseError;
import com.fleet.errors.ServerStatusError;
import com.fleet.models.TruckTrackingMessage;

import java.util.Properties;
import java.util.UUID;

public class TruckTrackingProducer {

    private TruckTrackingMessage message; 
        
    public TruckTrackingProducer(TruckTrackingMessage message) {
        this.message = message;
    }
        
    public void sendMessage() throws BaseTruckTrackingError {

        // Check if the topic exists, and create it if necessary
        ServerInterface.createTopicIfNotExists();

        // Kafka Producer configuration
        Properties properties = new Properties();
        properties.put("bootstrap.servers", ServerInterface.BOOTSTRAP_SERVERS);
        properties.put("key.serializer", StringSerializer.class.getName());
        properties.put("value.serializer", StringSerializer.class.getName());
    
        try (KafkaProducer<String, String> producer = new KafkaProducer<>(properties)) {
            
            // Convert message to JSON
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonMessage = objectMapper.writeValueAsString(this.message);

            // Try catch block to handle exceptions
            try {
                // Send message to Kafka
                ProducerRecord<String, String> record = new ProducerRecord<>(ServerInterface.TOPIC, UUID.randomUUID().toString(), jsonMessage);
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
