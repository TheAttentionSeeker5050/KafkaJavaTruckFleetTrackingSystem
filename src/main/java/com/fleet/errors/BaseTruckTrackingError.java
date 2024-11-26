package com.fleet.errors;

import java.util.Date;

public class BaseTruckTrackingError extends Exception {

    // use the following properties to store the error message
    public String message = "";
    public int errorCode = 0;
    public Date timestamp = new Date();
    
    public BaseTruckTrackingError(String message, int errorCode, Date timestamp) {
        this.message = message;
        this.errorCode = errorCode;
        this.timestamp = timestamp;
    }

    public BaseTruckTrackingError(String message, int errorCode) {
        this.message = message;
        this.errorCode = errorCode;
    }

    public BaseTruckTrackingError(String message) {
        this.message = message;
    }

}
