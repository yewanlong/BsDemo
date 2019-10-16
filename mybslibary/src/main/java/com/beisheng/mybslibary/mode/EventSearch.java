package com.beisheng.mybslibary.mode;

public class EventSearch {
    private String cache;
    private String content;

    public EventSearch(String cache, String content) {
        this.cache = cache;
        this.content = content;
    }

    public String getCache() {
        return cache;
    }

    public String getContent() {
        return content;
    }
}
