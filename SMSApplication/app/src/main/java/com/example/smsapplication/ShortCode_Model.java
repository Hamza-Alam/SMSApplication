package com.example.smsapplication;

public class ShortCode_Model {

    String id;
    String codeName;
    String codeString;

    public ShortCode_Model() {
        this.id = id;
        this.codeName = codeName;
        this.codeString = codeString;
    }


    public ShortCode_Model(String id, String codeString) {
        this.id = id;
        this.codeString = codeString;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCodeName() {
        return codeName;
    }

    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }

    public String getCodeString() {
        return codeString;
    }

    public void setCodeString(String codeString) {
        this.codeString = codeString;
    }
}
