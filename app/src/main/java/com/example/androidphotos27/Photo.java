package com.example.androidphotos27;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

/*
 * @author Travis Harrell (tsh61)
 * @author Elizaveta Belaya (edb81)
 */

public class Photo implements Serializable{

    private String fileName = "";
    private ArrayList<Tag> tagList = new ArrayList<>();
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
            tagList = new ArrayList<>();
            return tagList;
        }
        return tagList;
    }
    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject();
        int b;
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        while((b = ois.read()) != -1)
            byteStream.write(b);
        byte[] bitmapBytes = byteStream.toByteArray();
        pic = BitmapFactory.decodeByteArray(bitmapBytes, 0, bitmapBytes.length);
    }

    private void writeObject(ObjectOutputStream oos) throws IOException {
        oos.defaultWriteObject();
        if(pic != null){
            ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
            pic.compress(Bitmap.CompressFormat.PNG, 0, byteStream);
            byte[] bitmapBytes = byteStream.toByteArray();
            oos.write(bitmapBytes, 0, bitmapBytes.length);
        }
    }
}