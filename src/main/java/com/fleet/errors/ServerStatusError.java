package com.fleet.errors;

import java.util.Date;

public class ServerStatusError extends BaseTruckTrackingError {
	public ServerStatusError(String errorMessage, int errorCode, Date timestamp) {
        super(errorMessage, errorCode, timestamp);
    }

    public ServerStatusError(String errorMessage, int errorCode) {
        super(errorMessage, errorCode);
    }

    public ServerStatusError(String errorMessage) {
        super(errorMessage);
    }
}
