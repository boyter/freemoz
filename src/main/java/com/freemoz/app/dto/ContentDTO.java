package com.freemoz.app.dto;


import java.util.Arrays;
import java.util.List;

public class ContentDTO {
    private int id;
    private int parentid;
    private String topic;
    private String title;
    private String description;
    private String url;

    public ContentDTO(int id, int parentid, String topic, String title, String description, String url) {
        this.id = id;
        this.parentid = parentid;
        this.topic = topic;
        this.title = title;
        this.description = description;
        this.url = url;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getParentid() {
        return parentid;
    }

    public void setParentid(int parentid) {
        this.parentid = parentid;
    }

    public String getTopic() {
        return this.topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
