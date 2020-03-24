package com.coppel.rhconecta.dev.domain.home.entity;

public class Banner {

    private String id;
    private String src;
    private String image;
    private Byte type;

    public Banner(String id, String src, String image, Byte type) {
        this.id = id;
        this.src = src;
        this.image = image;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public String getSrc() {
        return src;
    }

    public String getImage() {
        return image;
    }

    public Byte getType() {
        return type;
    }

}
