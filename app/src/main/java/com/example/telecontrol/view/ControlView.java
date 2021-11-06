package com.example.telecontrol.view;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ControlView<T> {
    private T view;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("topic")
    @Expose
    private String topic;
    @SerializedName("name")
    @Expose
    private String name;

    public ControlView(T view, String topic) {
        this.topic = topic;
        this.view = view;
    }

    public ControlView(T view){
        this.view = view;
    }

    public T getView() {
        return view;
    }

    public void setView(T view) {
        this.view = view;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
