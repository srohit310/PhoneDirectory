package com.stancorp.phonedirectory.Classes;

import java.io.Serializable;

public class Name implements Serializable {
    String firstName;
    String lastName;

    public Name(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public boolean comp(Name name){
        if(this.firstName.compareTo(name.firstName) != 0 || this.lastName.compareTo(name.lastName) != 0)
            return false;
        return true;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
