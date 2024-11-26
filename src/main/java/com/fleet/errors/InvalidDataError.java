package com.fleet.errors;

import java.util.Date;

public class InvalidDataError extends BaseTruckTrackingError {
	public InvalidDataError(String errorMessage, int errorCode, Date timestamp) {
        super(errorMessage, errorCode, timestamp);
    }

    public InvalidDataError(String errorMessage, int errorCode) {
        super(errorMessage, errorCode);
    }

    public InvalidDataError(String errorMessage) {
        super(errorMessage);
    }
}