package org.fleetlogger;

import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.fleetlogger.errors.BaseTruckTrackingError;
import org.fleetlogger.errors.KafkaServerStatusError;
import org.fleetlogger.errors.MessageResponseError;
import org.fleetlogger.models.TruckTrackingMessage;
import org.fleetlogger.utils.KafkaServerInterface;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Properties;
import java.time.Duration;
import java.util.Collections;

public class TruckTrackingConsumer {

    public KafkaConsumer<String, String> consumer;
    public Properties properties;

    // Make a data structure that the consumer will use to modify the list of topics it is subscribed to
    public ConsumerRecords<String, String> records;
    
    private String bootstrapServers;
    private String topic;
    

    public TruckTrackingConsumer() {
        this(System.getenv().getOrDefault("KAFKA_BOOTSTRAP_SERVERS", KafkaServerInterface.BOOTSTRAP_SERVERS), System.getenv().getOrDefault("KAFKA_DEFAULT_TOPIC", KafkaServerInterface.TOPIC), System.getenv().getOrDefault("KAFKA_GROUP_ID", KafkaServerInterface.GROUP_ID));
    }

    // Another constructor where you can set up bootstrapServers and topic, and another one where you can also specify the group ID
    // This uses the constructor above to follow DRY rule
    public TruckTrackingConsumer(String bootstrapServers, String topic) {
        this(bootstrapServers, topic, System.getenv().getOrDefault("KAFKA_GROUP_ID", KafkaServerInterface.GROUP_ID));
    }

    public TruckTrackingConsumer(String bootstrapServers, String topic, String groupId) {
        // Set the bootstrap server and topic
        this.bootstrapServers = bootstrapServers;
        this.topic = topic;

        // Kafka Consumer configuration
        this.properties = new Properties();
        this.properties.put("bootstrap.servers", this.bootstrapServers);
        this.properties.put("group.id", groupId);
        this.properties.put("key.deserializer", StringDeserializer.class.getName());
        this.properties.put("value.deserializer", StringDeserializer.class.getName());
        this.properties.put("auto.offset.reset", "earliest");
    }


    public void subscribeToTopic() throws BaseTruckTrackingError {

        try {
            // Create a KafkaConsumer instance
            this.consumer = new KafkaConsumer<>(this.properties);

            // Subscribe to the topic
            this.consumer.subscribe(Collections.singletonList(this.topic));

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
            throw new KafkaServerStatusError(e.getMessage());
        }
    }
}
