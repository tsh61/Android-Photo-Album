package com.example.androidphotos27;

import java.util.ArrayList;
import java.io.Serializable;

/*
 * @author Travis Harrell (tsh61)
 * @author Elizaveta Belaya (edb81)
 */

public class Album implements Serializable{

    private String name;
    private ArrayList<Photo> photos;

    public Album(String n) {
        this.name = n;
        this.photos = new ArrayList<Photo>();
    }


    public void setName(String albumName) {
        name = albumName;
    }

    public String getName() {
        return name;
    }

    public void addPhotoToAlbum(Photo ph) {
        photos.add(ph);
    }

    public ArrayList<Photo> getPhotos() {
        return photos;
    }

    //method to get oldest Photo
    //method to get newest Photo

    public void deletePhoto(Photo ph) {
        photos.remove(ph);
    }

    @Override
    public String toString() {
        return name;
    }

}