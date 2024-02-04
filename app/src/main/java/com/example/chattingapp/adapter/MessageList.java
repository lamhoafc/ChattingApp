package com.example.chattingapp.adapter;

public class MessageList {
    private String email, lastMessage;
    private int unseenMessage;

    public MessageList(String email, String lastMessage, int unseenMessage) {
        this.email = email;
        this.lastMessage = lastMessage;
        this.unseenMessage = unseenMessage;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public int getUnseenMessage() {
        return unseenMessage;
    }

    public void setUnseenMessage(int unseenMessage) {
        this.unseenMessage = unseenMessage;
    }
}
