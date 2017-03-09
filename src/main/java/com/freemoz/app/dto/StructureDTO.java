package com.freemoz.app.dto;

import java.util.Arrays;
import java.util.List;

public class StructureDTO {
    private int id;
    private int parentid;
    private String topic;
    private String name;


    public StructureDTO(int id, int parentid, String topic) {
        this.id = id;
        this.parentid = parentid;
        this.topic = topic;

        List<String> strings = Arrays.asList(topic.split("/"));
        this.name = strings.get(strings.size()-1);
    }

    public int getId() {
        return id;
    }

    public int getParentid() {
        return parentid;
    }

    public String getTopic() {
        return topic;
    }

    public String getName() {
        return name;
    }
}
