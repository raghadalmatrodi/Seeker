package com.example.seeker.Model;

import com.stfalcon.chatkit.commons.models.IMessage;
import com.stfalcon.chatkit.commons.models.IUser;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.TimeZone;

public class ChatMessage implements Serializable, IMessage  {
    private long id;
    private String createdAt;
    private String message;
    private User sender;
    private Chat chat;

    public ChatMessage() {
    }

    public ChatMessage(long id, String createdAt, String message, User sender, Chat chat) {
        this.id = id;
        this.createdAt = createdAt;
        this.message = message;
        this.sender = sender;
        this.chat = chat;
    }

    public  String getId() {
        return id + "";
    }

    @Override
    public String getText() {
        return message;
    }

    @Override
    public IUser getUser() {
        return sender;
    }


    public void setId(long id) {
        this.id = id;
    }

    public Date getCreatedAt() {
        return ConvertToDate(createdAt);
    }


    public String getMessage() {
        return message;
    }

    public Date ConvertToDate(String createdAt){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
        LocalDateTime dateTime = LocalDateTime.parse(createdAt);

        //    String dateInString = "2014-10-05T15:23:01Z";
        Date date2 = Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());

        return date2;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }
}
