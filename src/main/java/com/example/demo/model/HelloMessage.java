package com.example.demo.model;

public class HelloMessage {

    private String strMessage;

    // No-arg constructor for JSON deserialization
    public HelloMessage() {}

    public HelloMessage(String inMsg) {
        this.strMessage = inMsg;
    }

    public String getStrMessage() {
        return strMessage;
    }

    public void setStrMessage(String strMessage) {
        this.strMessage = strMessage;
    }

}
