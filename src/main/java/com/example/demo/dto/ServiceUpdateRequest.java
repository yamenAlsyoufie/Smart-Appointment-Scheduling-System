package com.example.demo.dto;

public class ServiceUpdateRequest {

    private int duration;
    private double price;

    public int getDuration() {
        return duration;
    }

    public double getPrice() {
        return price;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
