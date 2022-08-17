package com.example.transit;

public class StopModel {
    String Departure;
    String Arrival;

    public StopModel() {
    }

    public StopModel(String departure, String arrival) {
        Departure = departure;
        Arrival = arrival;
    }

    public String getDeparture() {
        return Departure;
    }

    public void setDeparture(String departure) {
        Departure = departure;
    }

    public String getArrival() {
        return Arrival;
    }

    public void setArrival(String arrival) {
        Arrival = arrival;
    }
}
