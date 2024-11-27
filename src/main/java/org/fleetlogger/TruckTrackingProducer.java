package org.fleetlogger;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.fleetlogger.errors.BaseTruckTrackingError;
import org.fleetlogger.errors.KafkaServerStatusError;
import org.fleetlogger.errors.MessageResponseError;
import org.fleetlogger.models.TruckTrackingMessage;
import org.fleetlogger.utils.KafkaServerInterface;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Properties;
import java.util.UUID;

public class TruckTrackingProducer {

    private TruckTrackingMessage message;
    private String bootstrapServers;
    private String topic;

    public TruckTrackingProducer(TruckTrackingMessage message) {
        // this.message = message;
        this(message, System.getenv().getOrDefault("KAFKA_BOOTSTRAP_SERVERS", KafkaServerInterface.BOOTSTRAP_SERVERS), System.getenv().getOrDefault("KAFKA_DEFAULT_TOPIC", KafkaServerInterface.TOPIC));
    }

    public TruckTrackingProducer(TruckTrackingMessage message, String bootstrapServers) {
        this(message, bootstrapServers, System.getenv().getOrDefault("KAFKA_DEFAULT_TOPIC", KafkaServerInterface.TOPIC));
    }

    // Another constructor where you can set up bootstrapServers and topic
    public TruckTrackingProducer(TruckTrackingMessage message, String bootstrapServers, String topic) {
        this.message = message;
        this.bootstrapServers = bootstrapServers;
        this.topic = topic;
    }
        
    public void sendMessage() throws BaseTruckTrackingError {

        // Check if the topic exists, and create it if necessary
        KafkaServerInterface.createTopicIfNotExists();

        // Kafka Producer configuration
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
    
        try (KafkaProducer<String, String> producer = new KafkaProducer<>(properties)) {
            
            // Convert message to JSON
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonMessage = objectMapper.writeValueAsString(this.message);

            // Try catch block to handle exceptions
            try {
                // Send message to Kafka
                ProducerRecord<String, String> record = new ProducerRecord<>(topic, UUID.randomUUID().toString(), jsonMessage);
                producer.send(record);
                
            } catch (Exception e) {
                throw new MessageResponseError(e.getMessage());
            } finally {
                // Close producer
                producer.close();
            }

        } catch (Exception e) {
            throw new KafkaServerStatusError(e.getMessage());
        }
    }
}
