package com.cg.kiwi.model;

/**
 * Created by Saurabh on 6/20/2017.
 */

public class EventPhotos {

    String url;
    String thumbUrl;
    int order;
    int photoId;
    String type;

    public EventPhotos(String url, String thumbUrl, int order, int photoId, String type) {
        this.url = url;
        this.thumbUrl = thumbUrl;
        this.order = order;
        this.photoId = photoId;
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getThumbUrl() {
        return thumbUrl;
    }

    public void setThumbUrl(String thumbUrl) {
        this.thumbUrl = thumbUrl;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public int getPhotoId() {
        return photoId;
    }

    public void setPhotoId(int photoId) {
        this.photoId = photoId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
