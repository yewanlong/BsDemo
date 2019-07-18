package com.beisheng.mybslibary.imgsel.bean;

import java.util.ArrayList;
import java.util.List;

public class ImageSelEvent {
    private List<String> imageList = new ArrayList<>();

    public ImageSelEvent(List<String> list) {
        imageList.addAll(list);
    }

    public List<String> getImageList() {
        return imageList;
    }

    public void setImageList(List<String> imageList) {
        this.imageList = imageList;
    }
}
