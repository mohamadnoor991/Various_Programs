package com.semweb.semWebArt.model;

public class BikeStation {

    private String stationID;
    private String stationName;
    private int capacity;
    private int avalaibleBike;
    private int availableDock;
    private Double latitude;
    private Double longitude;

    // Lyon station extra informatin
    private String stationAddress ;
    private String commune ;
    private String  status;

    public String getStationID() {
        return stationID;
    }

    public void setStationID(String stationID) {
        this.stationID = stationID;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public int getAvalaibleBike() {
        return avalaibleBike;
    }

    public void setAvalaibleBike(int avalaibleBike) {
        this.avalaibleBike = avalaibleBike;
    }

    public int getAvailableDock() {
        return availableDock;
    }

    public void setAvailableDock(int availableDock) {
        this.availableDock = availableDock;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getStationAddress() {
        return stationAddress;
    }

    public void setStationAddress(String stationAddress) {
        this.stationAddress = stationAddress;
    }

    public String getCommune() {
        return commune;
    }

    public void setCommune(String commune) {
        this.commune = commune;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "BikeStation{" +
                "stationID='" + stationID + '\'' +
                ", stationName='" + stationName + '\'' +
                ", capacity=" + capacity +
                ", avalaibleBike=" + avalaibleBike +
                ", availableDock=" + availableDock +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", stationAddress='" + stationAddress + '\'' +
                ", commune='" + commune + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
