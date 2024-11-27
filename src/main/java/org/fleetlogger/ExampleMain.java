package org.fleetlogger;

import org.fleetlogger.errors.BaseTruckTrackingError;
import org.fleetlogger.errors.KafkaServerStatusError;
import org.fleetlogger.errors.MessageResponseError;
import org.fleetlogger.models.TruckTrackingMessage;

// This is just a test file to run the producer and consumer
// This file is not part of the main application and will be removed in the future
// Next stage is to make this logic part of the unit tests
public class ExampleMain {

    public static void main(String[] args) {

        // System.out.println("starting program");
        
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
        
        // Create a TruckTrackingProducer instance
        TruckTrackingProducer producerInstance = new TruckTrackingProducer(message);

        // Try catch emit a message
        try {
            producerInstance.sendMessage();
        } catch (KafkaServerStatusError error) {
            System.out.println("ServerStatusError: " + error.getMessage());

        } catch (MessageResponseError error) {
            System.out.println("MessageResponseError: " + error.getMessage());

        } catch (BaseTruckTrackingError error) {
            System.out.println("BaseTruckTrackingError: " + error.getMessage());
        } finally {
            consume();
            System.out.println("Closing producer");
        }
    }

    public static void consume() {
        // Create a TruckTrackingConsumer instance
        TruckTrackingConsumer consumerInstance = new TruckTrackingConsumer();

        // Try catch emit a message
        try {
            consumerInstance.subscribeToTopic();
        } catch (KafkaServerStatusError error) {
            System.out.println("ServerStatusError: " + error.getMessage());
        } catch (BaseTruckTrackingError error) {
            System.out.println("BaseTruckTrackingError: " + error.getMessage());
        } finally {
            System.out.println("Consumer Instance records: " + consumerInstance.records);
        }

    }
}
