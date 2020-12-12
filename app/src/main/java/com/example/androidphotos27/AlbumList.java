package com.example.androidphotos27;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
/*
 * @author Travis Harrell (tsh61)
 * @author Elizaveta Belaya (edb81)
 */
public class AlbumList implements Serializable {
    ArrayList<Album> albList = new ArrayList<Album>();
    public static final long serialVersionUID = 1L;
    public static final String file = "albums.serial";

    public static AlbumList read(Context context){
        AlbumList list = null;
        try{
            FileInputStream fis = context.openFileInput(file);
            ObjectInputStream ois = new ObjectInputStream(fis);
            list = (AlbumList) ois.readObject();

            if(list.albList == null){
                list.albList = new ArrayList<Album>();
            }
            fis.close();
            ois.close();
        }catch(Exception exception){
            return null;
        }
        return list;
    }

    public void write(Context context){
        try{
            FileOutputStream fos = context.openFileOutput(file, Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(this);
            fos.close();
            oos.close();
        }catch(IOException exception){
            exception.printStackTrace();
        }
    }
}
