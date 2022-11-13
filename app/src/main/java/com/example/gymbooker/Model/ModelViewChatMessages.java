package com.example.gymbooker.Model;

public class ModelViewChatMessages {

    String inMessage, inSenderId;
    long inTimeStamp;
    String inCurrentTime;

    public ModelViewChatMessages() {

    }

    public ModelViewChatMessages(String Message, String senderId, long TimeStamp, String CurrentTime) {
        inMessage = Message;
        inSenderId = senderId;
        inTimeStamp = TimeStamp;
        inCurrentTime = CurrentTime;
    }

    public String getInMessage() {
        return inMessage;
    }

    public void setInMessage(String inMessage) {
        this.inMessage = inMessage;
    }

    public String getInSenderId() {
        return inSenderId;
    }

    public void setInSenderId(String inSenderId) {
        this.inSenderId = inSenderId;
    }

    public long getInTimeStamp() {
        return inTimeStamp;
    }

    public void setInTimeStamp(long inTimeStamp) {
        this.inTimeStamp = inTimeStamp;
    }

    public String getInCurrentTime() {
        return inCurrentTime;
    }

    public void setInCurrentTime(String inCurrentTime) {
        this.inCurrentTime = inCurrentTime;
    }
}
