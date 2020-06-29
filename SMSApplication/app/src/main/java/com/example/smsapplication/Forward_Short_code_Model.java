package com.example.smsapplication;

public class Forward_Short_code_Model {

    String id;
    String codeName;
    String codeString;
    String forwardString;

    public Forward_Short_code_Model() {
        this.id = id;
        this.codeName = codeName;
        this.codeString = codeString;
        this.forwardString = forwardString;
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

    public String getForwardString() {
        return forwardString;
    }

    public void setForwardString(String forwardString) {
        this.forwardString = forwardString;
    }
}
