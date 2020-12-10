package com.example.androidphotos27;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    private Button add, delete, rename, open, search;
    private ListView albumList;
    private Context context = this;
    private int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setTitle("My Albums");

        add = findViewById(R.id.add);
        delete = findViewById(R.id.delete);
        rename = findViewById(R.id.rename);
        open = findViewById(R.id.open);
        search = findViewById(R.id.search);
        albumList = findViewById(R.id.albList);

    }

    protected void onOpenClick(){
        open.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent startIntent = new Intent(context, OpenedAlbum.class);
               // startIntent.putExtra(ALBUM_ID, index);
                startActivity(startIntent);
            }

        });

    }
}