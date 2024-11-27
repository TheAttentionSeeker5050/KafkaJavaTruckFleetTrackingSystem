package com.fleet.errors;

import java.util.Date;

public class KafkaServerStatusError extends BaseTruckTrackingError {
	public KafkaServerStatusError(String errorMessage, int errorCode, Date timestamp) {
        super(errorMessage, errorCode, timestamp);
    }

    public KafkaServerStatusError(String errorMessage, int errorCode) {
        super(errorMessage, errorCode);
    }

    public KafkaServerStatusError(String errorMessage) {
        super(errorMessage);
    }
}
