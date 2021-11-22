package com.stancorp.phonedirectory.Classes;

import java.io.Serializable;
import java.util.ArrayList;

public class ContactDetails implements Serializable {
    private Name name;
    private ArrayList<PhoneNumber> phoneNumbers;
    private String email;
    private Address address;
    private String notes;

    public ContactDetails(){}

    public ContactDetails(Name name,ArrayList<PhoneNumber> phoneNumberArrayList){
        this.phoneNumbers = phoneNumberArrayList;
        this.name = name;
    }

    public ContactDetails(Name name, ArrayList<PhoneNumber> phone, String email, Address address, String notes) {
        this.name = name;
        this.phoneNumbers = phone;
        this.email = email;
        this.address = address;
        this.notes = notes;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public void setPhoneNumbers(ArrayList<PhoneNumber> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Name getName() {
        return name;
    }

    public ArrayList<PhoneNumber> getPhone() {
        return phoneNumbers;
    }

    public String getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    public String getNotes() {
        return notes;
    }
}
