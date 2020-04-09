package com.example.seeker.Model;

public class StorageDocument {

    private long id;
    private String name;
    private StorageEnum type;
    private String url;
    private String contentType;

    public StorageDocument() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public StorageEnum getType() {
        return type;
    }

    public void setType(StorageEnum type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public StorageDocument(long id, String name, StorageEnum type, String url, String contentType) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.url = url;
        this.contentType = contentType;
    }
}
