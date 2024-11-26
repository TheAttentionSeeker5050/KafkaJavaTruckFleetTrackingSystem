package com.fleet.errors;

import java.util.Date;

public class BaseTruckTrackingError extends Exception {

    // use the following properties to store the error message
    public String errorMessage = "";
    public int errorCode = 0;
    public Date timestamp = new Date();
    
    public BaseTruckTrackingError(String errorMessage, int errorCode, Date timestamp) {
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
        this.timestamp = timestamp;
    }

    public BaseTruckTrackingError(String errorMessage, int errorCode) {
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
    }

    public BaseTruckTrackingError(String errorMessage) {
        this.errorMessage = errorMessage;
    }

}
