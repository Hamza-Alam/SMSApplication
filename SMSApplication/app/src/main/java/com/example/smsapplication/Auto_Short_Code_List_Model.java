package com.example.smsapplication;

public class Auto_Short_Code_List_Model {
    String id;
    String name;
    String SampleString;
    String BuildString;
    String number;
    String functionality;

    public Auto_Short_Code_List_Model(String id, String name, String sampleString, String buildString, String number, String functionality) {
        this.id = id;
        this.name = name;
        SampleString = sampleString;
        BuildString = buildString;
        this.number = number;
        this.functionality = functionality;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSampleString() {
        return SampleString;
    }

    public void setSampleString(String sampleString) {
        SampleString = sampleString;
    }

    public String getBuildString() {
        return BuildString;
    }

    public void setBuildString(String buildString) {
        BuildString = buildString;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getFunctionality() {
        return functionality;
    }

    public void setFunctionality(String functionality) {
        this.functionality = functionality;
    }

}
