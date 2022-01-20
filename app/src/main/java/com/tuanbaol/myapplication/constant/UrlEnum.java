package com.tuanbaol.myapplication.constant;

public enum UrlEnum {
    MESSAGE_SERVER("http://www.tuanbaol.com:8099/messageserver/message/obtain");
    private String url;

    UrlEnum(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
