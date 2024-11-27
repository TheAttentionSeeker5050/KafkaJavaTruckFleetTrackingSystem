package org.fleetlogger.utils;

import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.CreateTopicsResult;
import org.apache.kafka.clients.admin.ListTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;

// Interface for the TruckTrackingProducer and TruckTrackingConsumer classes
public interface KafkaServerInterface {

    // App Constants
    public static final String TOPIC = "travel-log";
    public static final String BOOTSTRAP_SERVERS = "localhost:9091";
    public static final String GROUP_ID = "fleet-tracking-group";

    

    static void createTopicIfNotExists() {
        String bootstrapServers = System.getenv().getOrDefault("KAFKA_BOOTSTRAP_SERVERS", BOOTSTRAP_SERVERS);
        String topic = System.getenv().getOrDefault("KAFKA_DEFAULT_TOPIC", TOPIC);

        Properties adminProps = new Properties();
        adminProps.put("bootstrap.servers", bootstrapServers);
        AdminClient adminClient = AdminClient.create(adminProps);

        try {
            ListTopicsResult topics = adminClient.listTopics();
            Set<String> topicNames = topics.names().get();

            // Check if the topic exists
            if (!topicNames.contains(topic)) {
                // If not, create the topic with 1 partition and replication factor of 1
                NewTopic newTopic = new NewTopic(topic, 1, (short) 1);  // 1 partition, replication factor 1
                CreateTopicsResult result = adminClient.createTopics(java.util.Collections.singletonList(newTopic));
                result.all().get();  // Wait until the topic is created
            }
        } catch (ExecutionException | InterruptedException e) {
            System.out.println("Error creating topic: " + e.getMessage());
        } finally {
            adminClient.close();
        }
    }
}