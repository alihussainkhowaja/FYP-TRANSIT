package com.example.admintranis;

public class HistoryModel {

    String Arrival,Date,Departure,Name;
    int Amount;

    HistoryModel(){

    }

    public HistoryModel(String arrival, String date, String departure, String name, int price) {
        Arrival = arrival;
        Date = date;
        Departure = departure;
        Name = name;
        Amount = price;
    }

    public String getArrival() {
        return Arrival;
    }

    public void setArrival(String arrival) {
        Arrival = arrival;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getDeparture() {
        return Departure;
    }

    public void setDeparture(String departure) {
        Departure = departure;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getAmount() {
        return Amount;
    }

    public void setAmount(int amount) {
        Amount = amount;
    }
}
