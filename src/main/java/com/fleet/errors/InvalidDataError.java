package com.fleet.errors;

import java.util.Date;

public class InvalidDataError extends BaseTruckTrackingError {
	public InvalidDataError(String message, int errorCode, Date timestamp) {
        super(message, errorCode, timestamp);
    }

    public InvalidDataError(String message, int errorCode) {
        super(message, errorCode);
    }

    public InvalidDataError(String message) {
        super(message);
    }
}