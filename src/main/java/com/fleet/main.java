package com.fleet;

import com.fleet.errors.BaseTruckTrackingError;
import com.fleet.errors.MessageResponseError;
import com.fleet.errors.ServerStatusError;

public class main {

    public static void main(String[] args) {

        System.out.println("starting program");
        
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
        } catch (ServerStatusError error) {
            // TODO: handle exception
            System.out.println("ServerStatusError: " + error.getMessage());

        } catch (MessageResponseError error) {
            // TODO: handle exception
            System.out.println("MessageResponseError: " + error.getMessage());

        } catch (BaseTruckTrackingError error) {
            // TODO Auto-generated catch block
            System.out.println("BaseTruckTrackingError: " + error.getMessage());

            // error.printStackTrace();
        }
    }

    
}
