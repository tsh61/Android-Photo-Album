package com.example.androidphotos27;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    private Button add, delete, rename, open, search;
    private ListView AlbumList;

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
    }
}