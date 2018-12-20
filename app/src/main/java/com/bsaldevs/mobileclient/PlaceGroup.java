package com.bsaldevs.mobileclient;

public class PlaceGroup {

    private String name;
    private int imageResourceID;

    public PlaceGroup(String name, int imageResourceID) {
        this.name = name;
        this.imageResourceID = imageResourceID;
    }

    public String getName() {
        return name;
    }

    public int getImageResourceID() {
        return imageResourceID;
    }

}
