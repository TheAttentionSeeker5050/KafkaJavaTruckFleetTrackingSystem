package com.fleet.models;

import com.fleet.common.ValidValue;

// Import validation annotations
import jakarta.validation.constraints.*;
import jakarta.validation.constraints.AssertFalse.List;

public class TruckTrackingMessageWithValidations extends TruckTrackingMessage {

     // Categorical values as constants
    // Status
    public static final String STATUS_IN_TRANSIT = "In Transit";
    public static final String STATUS_ARRIVED = "Arrived";
    public static final String STATUS_DELAYED = "Delayed";
    public static final String STATUS_DELIVERED = "Delivered";

    public static final String[] ValidStatusValues = {STATUS_IN_TRANSIT, STATUS_ARRIVED, STATUS_DELAYED, STATUS_DELIVERED};

    // Load Type
    public static final String LOAD_TYPE_DRY_GOODS = "Dry Goods";
    public static final String LOAD_TYPE_REFRIGERATED_GOODS = "Refrigerated Goods";
    public static final String LOAD_TYPE_LIQUID = "Liquid";
    public static final String LOAD_TYPE_HAZARDOUS = "Hazardous";

    public static final String[] ValidLoadTypeValues = {LOAD_TYPE_DRY_GOODS, LOAD_TYPE_REFRIGERATED_GOODS, LOAD_TYPE_LIQUID, LOAD_TYPE_HAZARDOUS};

    // Truck Type
    public static final String TRUCK_TYPE_DRY_VAN = "Dry Van";
    public static final String TRUCK_TYPE_REFRIGERATED = "Refrigerated";
    public static final String TRUCK_TYPE_TANKER = "Tanker";
    public static final String TRUCK_TYPE_FLATBED = "Flatbed";
    public static final String TRUCK_TYPE_LTL = "LTL";
    public static final String TRUCK_TYPE_INTERMODAL = "Intermodal";
    public static final String TRUCK_TYPE_STEP_DECK = "Step Deck";

    public static final String[] ValidTruckTypeValues = {TRUCK_TYPE_DRY_VAN, TRUCK_TYPE_REFRIGERATED, TRUCK_TYPE_TANKER, TRUCK_TYPE_FLATBED, TRUCK_TYPE_LTL, TRUCK_TYPE_INTERMODAL, TRUCK_TYPE_STEP_DECK};
    
    @NotBlank(message = "Truck ID is mandatory")
    private String truckId;

    // Categorical values as constants, use truck type
    @NotBlank(message = "Truck Type is mandatory")
    @ValidValue(acceptedValues = {TRUCK_TYPE_DRY_VAN, TRUCK_TYPE_REFRIGERATED, TRUCK_TYPE_TANKER, TRUCK_TYPE_FLATBED, TRUCK_TYPE_LTL, TRUCK_TYPE_INTERMODAL, TRUCK_TYPE_STEP_DECK})
    private String truckType;
    @NotBlank(message = "Load Type is mandatory")
    @ValidValue(acceptedValues = {LOAD_TYPE_DRY_GOODS, LOAD_TYPE_REFRIGERATED_GOODS, LOAD_TYPE_LIQUID, LOAD_TYPE_HAZARDOUS})
    private String loadType;
    @NotNull(message = "Loaded is mandatory")
    private boolean loaded;
    @Min(value = 0, message = "Load Weight must be greater than 0")
    private int loadWeight;
    @NotBlank(message = "Status is mandatory")
    @ValidValue(acceptedValues = {STATUS_IN_TRANSIT, STATUS_ARRIVED, STATUS_DELAYED, STATUS_DELIVERED})
    private String status;
    @NotBlank(message = "Origin is mandatory")
    private String origin;
    @NotBlank(message = "Destination is mandatory")
    private String destination;
    @NotBlank(message = "Current Location is mandatory")
    private String currentLocation;
    @Min(value = 0, message = "Miles Traveled must be greater than 0")
    private int milesTraveled;
    @Min(value = 0, message = "Mileage must be greater than 0")
    private double mileage;
    @NotBlank(message = "ETA is mandatory")
    private String eta;


    // Constructor, Getters, and Setters
    public TruckTrackingMessageWithValidations(String truckId, String truckType, String loadType, boolean loaded, int loadWeight,
                                String status, String origin, String destination, String currentLocation, 
                                int milesTraveled, double mileage, String eta) {
        super(truckId, truckType, loadType, loaded, loadWeight, status, origin, destination, currentLocation, milesTraveled, mileage, eta);
    }
}
