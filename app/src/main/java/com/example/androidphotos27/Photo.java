package com.example.androidphotos27;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.Serializable;
import java.util.ArrayList;

/*
 * @author Travis Harrell (tsh61)
 * @author Elizaveta Belaya (edb81)
 */

public class Photo implements Serializable{

    private String fileName = "";
    private ArrayList<Tag> tagList = new ArrayList<Tag>();
    transient Bitmap pic;

    public Photo(Bitmap pic) {
        this.pic = pic;
    }

    public Bitmap getPic() {
        return pic;
    }

//	public SerialPhoto getSerialPhoto() {

//		return pic;

//	}
    public String getFileName(){
        return fileName;
    }

    public void setFileName(String fileName){
        this.fileName = fileName;
    }

    public void addTag(String name, String value) {
        tagList.add(new Tag(name, value));
    }

    public void removeTag(int index) {
        tagList.remove(index);
    }
    public ArrayList<Tag> getTags() {
        if(tagList == null){
            tagList = new ArrayList<Tag>();
            return tagList;
        }
        return tagList;
    }
}