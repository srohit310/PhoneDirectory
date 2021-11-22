package com.stancorp.phonedirectory.Classes;

import java.io.Serializable;

public class Address implements Serializable {

    private String street;
    private String city;
    private String state;
    private String country;
    private Long zipCode;

    public Address(String street, String city, String state, String country, Long zipCode){
        this.street = street;
        this.country = country;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
    }

    public String getStreet() {
        return street;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getCountry() {
        return country;
    }

    public Long getZipCode() {
        return zipCode;
    }
}
