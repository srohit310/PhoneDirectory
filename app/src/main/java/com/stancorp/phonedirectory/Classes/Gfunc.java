package com.stancorp.phonedirectory.Classes;

import android.text.format.DateFormat;

import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

public class Gfunc {

    public String getMonth(int month) {
        return new DateFormatSymbols().getMonths()[month-1];
    }


    public float roundof(float digit,int numberofplaces){
        float no_of_places = (float) Math.pow(10,numberofplaces);
        float roundOff = (float) (Math.round(digit * no_of_places) / no_of_places);
        return roundOff;
    }

    public String getYesterdayDate(String Format) {
        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        String dateFormat = (String) DateFormat.format(Format,cal.getTime());
        return dateFormat;
    }

    public String getCurrentDate(String Format){
        Date date= new Date();
        String currentdate = (String) DateFormat.format(Format, date.getTime());
        return  currentdate;
    }

    public boolean isEmailValid(String email)
    {
        if(email.length() == 0){
            return false;
        }
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }

    public boolean checkifcharexistsmorethanonce(String s, char a) {
        int count = 0;
        if(s.length()==0){
            return false;
        }
        boolean charflag = false;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == a) {
                count++;
            }
            if (count == 2) {
                charflag = true;
                break;
            }
        }
        return charflag;
    }

    public String capitalize(String s){
        String result="";
        String temp[];
        if(s.length() == 0) return s;
        temp = (s.split(" "));
        for(int i=0;i<temp.length;i++){
            result += temp[i].substring(0, 1).toUpperCase() + temp[i].substring(1).toLowerCase();
            if(i != temp.length-1)
                result += ' ';
        }
        return result;
    }
}
