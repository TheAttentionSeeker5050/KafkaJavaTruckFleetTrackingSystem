package com.fleet.errors;

import java.util.Date;

public class ServerStatusError extends BaseTruckTrackingError {
	public ServerStatusError(String message, int errorCode, Date timestamp) {
        super(message, errorCode, timestamp);
    }

    public ServerStatusError(String message, int errorCode) {
        super(message, errorCode);
    }

    public ServerStatusError(String message) {
        super(message);
    }
}
