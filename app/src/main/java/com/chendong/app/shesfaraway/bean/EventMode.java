package com.chendong.app.shesfaraway.bean;

import com.avos.avoscloud.AVObject;

import java.util.Date;

/**
 * Created by Administrator on 2017/4/26.
 */
public class EventMode {

    String title;
    String content;
    Date time;
    String color;

    public EventMode() {

    }

    public EventMode(AVObject data) {
        title = data.getString("title");
        content = data.getString("content");
        color = data.getString("color");
        time = data.getDate("time");
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
