package org.fleetlogger.errors;

import java.util.Date;

public class MessageResponseError extends BaseTruckTrackingError {
	public MessageResponseError(String errorMessage, int errorCode, Date timestamp) {
        super(errorMessage, errorCode, timestamp);
    }

    public MessageResponseError(String errorMessage, int errorCode) {
        super(errorMessage, errorCode);
    }

    public MessageResponseError(String errorMessage) {
        super(errorMessage);
    }
}