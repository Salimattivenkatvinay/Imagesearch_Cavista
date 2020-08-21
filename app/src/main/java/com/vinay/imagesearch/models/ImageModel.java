package com.vinay.imagesearch.models;

import java.io.Serializable;

public class ImageModel implements Serializable {

    private String id;
    private String link;
    private String title;

    public ImageModel(String id, String link, String title) {
        this.id = id;
        this.link = link;
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
