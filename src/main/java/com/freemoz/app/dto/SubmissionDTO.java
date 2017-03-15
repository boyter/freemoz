package com.freemoz.app.dto;

public class SubmissionDTO {
    public String url;
    public String title;
    public String description;
    public String category;
    public String tags;

    public SubmissionDTO(String url, String title, String description, String category, String tags) {
        this.url = url;
        this.title = title;
        this.description = description;
        this.category = category;
        this.tags = tags;
    }
}
