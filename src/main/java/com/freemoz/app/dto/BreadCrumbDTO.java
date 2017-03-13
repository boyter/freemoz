package com.freemoz.app.dto;

public class BreadCrumbDTO {
    private String path;
    private String name;

    public BreadCrumbDTO(String path, String name) {
        this.path = path;
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public String getName() {
        return name;
    }
}
