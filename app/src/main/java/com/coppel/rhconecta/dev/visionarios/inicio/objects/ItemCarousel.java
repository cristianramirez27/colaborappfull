package com.coppel.rhconecta.dev.visionarios.inicio.objects;

public class ItemCarousel {

    private int id;
    private int typeItem; //1== comunicado 2=videos
    private int idxItem; // idx del arraylist ya sea de comunicados o videos

    public ItemCarousel() {
    }

    public ItemCarousel(int id, int typeItem, int idxItem) {
        this.id = id;
        this.typeItem = typeItem;
        this.idxItem = idxItem;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTypeItem() {
        return typeItem;
    }

    public void setTypeItem(int typeItem) {
        this.typeItem = typeItem;
    }

    public int getIdxItem() {
        return idxItem;
    }

    public void setIdxItem(int idxItem) {
        this.idxItem = idxItem;
    }
}
