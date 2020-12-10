package com.example.androidphotos27;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.widget.Button;
import android.widget.GridView;

public class OpenedAlbum extends AppCompatActivity {

    public static final String ALBUM_ID = "albumID";
    private int albumID;
    private Button add, delete, display, move;
    private GridView thumbnails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.opened_album_display);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            albumID = bundle.getInt(ALBUM_ID);
        }

        this.setTitle(MainActivity.mainList.albList.get(albumID).toString());

        add = findViewById(R.id.addPhoto);
        delete = findViewById(R.id.deletePhoto);
        display = findViewById(R.id.displayPhoto);
        move = findViewById(R.id.movePhoto);
        thumbnails = findViewById(R.id.searchThumbnails);
    }
}