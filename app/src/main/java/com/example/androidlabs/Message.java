package com.example.androidlabs;

public class Message {

    private String message;
    private boolean isSent;
    private long messageID;


    public Message(){
    }

    public Message(long messageID, String message, boolean isSent){
        this.messageID = messageID;
        this.message = message;
        this.isSent = isSent;
    }

    public Message(String message, boolean isSent){
        this(0, message, isSent);
    }

    public void setMessage(String message){
        this.message = message;
    }

    public String getMessage(){
        return message;
    }

    public void setSent(boolean isSent){
        this.isSent = isSent;
    }

    public boolean isSent(){
        return isSent;
    }

    public void setMessageId(long messageID){
        this.messageID = messageID;
    }

    public long getMessageId(){
        return messageID;
    }
}