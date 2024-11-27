package org.fleetlogger.models;

public class TruckTrackingMessage {
    private String truckId;
    private String truckType;
    private String loadType;
    private boolean loaded;
    private int loadWeight;
    private String status;
    private String origin;
    private String destination;
    private String currentLocation;
    private int milesTraveled;
    private double mileage;
    private String eta;

    // Constructor, Getters, and Setters
    public TruckTrackingMessage(String truckId, String truckType, String loadType, boolean loaded, int loadWeight,
                                String status, String origin, String destination, String currentLocation, 
                                int milesTraveled, double mileage, String eta) {
        this.truckId = truckId;
        this.truckType = truckType;
        this.loadType = loadType;
        this.loaded = loaded;
        this.loadWeight = loadWeight;
        this.status = status;
        this.origin = origin;
        this.destination = destination;
        this.currentLocation = currentLocation;
        this.milesTraveled = milesTraveled;
        this.mileage = mileage;
        this.eta = eta;
    }

    // Getters and Setters
    public String getTruckId() {
        return truckId;
    }

    public void setTruckId(String truckId) {
        this.truckId = truckId;
    }

    public String getTruckType() {
        return truckType;
    }

    public void setTruckType(String truckType) {
        this.truckType = truckType;
    }

    public String getLoadType() {
        return loadType;
    }

    public void setLoadType(String loadType) {
        this.loadType = loadType;
    }

    public boolean isLoaded() {
        return loaded;
    }

    public void setLoaded(boolean loaded) {
        this.loaded = loaded;
    }

    public int getLoadWeight() {
        return loadWeight;
    }

    public void setLoadWeight(int loadWeight) {
        this.loadWeight = loadWeight;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(String currentLocation) {
        this.currentLocation = currentLocation;
    }

    public int getMilesTraveled() {
        return milesTraveled;
    }

    public void setMilesTraveled(int milesTraveled) {
        this.milesTraveled = milesTraveled;
    }

    public double getMileage() {
        return mileage;
    }

    public void setMileage(double mileage) {
        this.mileage = mileage;
    }

    public String getEta() {
        return eta;
    }

    public void setEta(String eta) {
        this.eta = eta;
    }
}
