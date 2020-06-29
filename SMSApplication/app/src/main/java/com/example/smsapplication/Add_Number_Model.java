package com.example.smsapplication;

public class Add_Number_Model {

    String numberID;
    String numberName;
    String number;

    public Add_Number_Model()
    {
        this.numberName = numberName;
        this.number = number;
        this.numberID= numberID;
    }

    public Add_Number_Model(String numberID, String numberName, String number) {
        this.numberID = numberID;
        this.numberName = numberName;
        this.number = number;
    }

    public String getNumberID() {
        return numberID;
    }

    public void setNumberID(String numberID) {
        this.numberID = numberID;
    }

    public String getNumberName() {
        return numberName;
    }

    public void setNumberName(String numberName) {
        this.numberName = numberName;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
