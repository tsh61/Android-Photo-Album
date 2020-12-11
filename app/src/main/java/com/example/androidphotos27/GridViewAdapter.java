package com.example.androidphotos27;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class GridViewAdapter extends ArrayAdapter {
    private Context context;
    private ArrayList thumbnails;

    public GridViewAdapter(Context context, ArrayList thumbnails){
        super(context, R.layout.thumb_item, thumbnails);
        this.context = context;
        this.thumbnails = thumbnails;
    }
    @Override
    public View getView(int pos, View curr, ViewGroup parent){
        if(curr == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            curr = inflater.inflate(R.layout.thumb_item, parent, false);
        }
        ImageView image = curr.findViewById(R.id.thumbDisplay);
        TextView name = curr.findViewById(R.id.thumbName);
        Photo pic = (Photo) thumbnails.get(pos);
        image.setImageBitmap(pic.getPic());
        name.setText(pic.getFileName());

        return curr;
    }
}
