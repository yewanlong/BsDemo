package com.beisheng.mybslibary.imgsel.bean;

import java.util.ArrayList;
import java.util.List;

public class ImageSelEvent {
    private List<String> imageList = new ArrayList<>();
    private int remove;

    public ImageSelEvent(List<String> list) {
        imageList.addAll(list);
    }

    public ImageSelEvent(List<String> list, int remove) {
        imageList.addAll(list);
        this.remove = remove;
    }

    public int getRemove() {
        return remove;
    }

    public void setRemove(int remove) {
        this.remove = remove;
    }

    public List<String> getImageList() {
        return imageList;
    }

    public void setImageList(List<String> imageList) {
        this.imageList = imageList;
    }
}
