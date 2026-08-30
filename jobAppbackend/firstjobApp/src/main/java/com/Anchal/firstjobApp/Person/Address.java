package com.Anchal.firstjobApp.Person;

import jakarta.persistence.Embeddable;

@Embeddable 
public class Address {

    private int houseno;
    private String street;
    private String city;
    private String State;
    private String country;
    private Long pincode;

    public Address(String street, int houseno, String city, String state, String country, Long pincode) {
        this.street = street;
        this.houseno = houseno;
        this.city = city;
        this.State = state;
        this.country = country;
        this.pincode = pincode;
    }

    public Address() {
    }

    public int getHouseNO() {
        return houseno;
    }

    public void setHouseNO(int houseno) {
        this.houseno = houseno;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        this.State = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Long getPincode() {
        return pincode;
    }

    public void setPincode(Long pincode) {
        this.pincode = pincode;
    }
}
