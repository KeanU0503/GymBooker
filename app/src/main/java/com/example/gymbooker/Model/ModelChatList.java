package com.example.gymbooker.Model;

public class ModelChatList { String inName, inUid;

    public ModelChatList(String Name, String Uid) {
        inName = Name;
        inUid = Uid;
    }

    public String getInName() {
        return inName;
    }

    public void setInName(String inName) {
        this.inName = inName;
    }

    public String getInUid() {
        return inUid;
    }

    public void setInUid(String inUid) {
        this.inUid = inUid;
    }
}
