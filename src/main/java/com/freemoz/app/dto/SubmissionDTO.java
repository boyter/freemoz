package com.freemoz.app.dto;

public class SubmissionDTO {
    public String url;
    public String title;
    public String description;

    public SubmissionDTO(String url, String title, String description) {
        this.url = url;
        this.title = title;
        this.description = description;
    }
}
