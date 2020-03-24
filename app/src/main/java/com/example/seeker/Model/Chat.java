package com.example.seeker.Model;

import com.stfalcon.chatkit.commons.models.IMessage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Chat implements Serializable {
    private long id;
    private User firstUser;
    private User secondUser;
    private List<ChatMessage> chatMessages = new ArrayList<>();

    public Chat(long id, User firstUser, User secondUser) {
        this.id = id;
        this.firstUser = firstUser;
        this.secondUser = secondUser;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getFirstUser() {
        return firstUser;
    }

    public void setFirstUser(User firstUser) {
        this.firstUser = firstUser;
    }

    public User getSecondUser() {
        return secondUser;
    }

    public void setSecondUser(User secondUser) {
        this.secondUser = secondUser;
    }

    public List<ChatMessage> getChatMessages() {
        return chatMessages;
    }

    public void setChatMessages(List<ChatMessage> chatMessages) {
        this.chatMessages = chatMessages;
    }

    @Override
    public String toString() {
        return "Chat{" +
                "id=" + id +
                ", firstUser=" + firstUser +
                ", secondUser=" + secondUser +
                ", chatMessages=" + chatMessages +
                '}';
    }
}
