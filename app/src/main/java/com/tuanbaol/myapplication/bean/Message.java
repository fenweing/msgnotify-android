package com.tuanbaol.myapplication.bean;

public class Message {
    private String title;
    private String ticker;
    private String text;
    private String srcPack;
    private String time;

    public Message() {
    }

    public Message(String title, String srcPack) {
        this.title = title;
        this.srcPack = srcPack;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSrcPack() {
        return srcPack;
    }

    public void setSrcPack(String srcPack) {
        this.srcPack = srcPack;
    }
}
