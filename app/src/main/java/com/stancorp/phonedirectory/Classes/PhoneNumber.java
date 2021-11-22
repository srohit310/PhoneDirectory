package com.stancorp.phonedirectory.Classes;

import java.io.Serializable;

public class PhoneNumber implements Serializable {
    private Long phoneNumber;
    private String Type;

    public PhoneNumber(){}

    public PhoneNumber(long phoneNumber, String type) {
        this.phoneNumber = phoneNumber;
        Type = type;
    }


    public Long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }
}
