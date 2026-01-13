package com.example.demo.dto;

public class ServiceRequest {

    private String name;
    private int duration;
    private double price;
    private Long providerId;

    public String getName() {
        return name;
    }

    public int getDuration() {
        return duration;
    }

    public double getPrice() {
        return price;
    }

    public Long getProviderId() {
        return providerId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setProviderId(Long providerId) {
        this.providerId = providerId;
    }
}

