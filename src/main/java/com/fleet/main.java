package com.fleet;

import com.fleet.errors.BaseTruckTrackingError;
import com.fleet.errors.MessageResponseError;
import com.fleet.errors.KafkaServerStatusError;
import com.fleet.models.TruckTrackingMessage;

public class main {

    public static void main(String[] args) {

        // System.out.println("starting program");
        
        TruckTrackingMessage message = new TruckTrackingMessage(
            "T12345 NEw", 
            "Refrigerated NEw", 
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
