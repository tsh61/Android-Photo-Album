package com.example.androidphotos27;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Button add, delete, rename, open, search;
    private ListView albumList;
    public static AlbumList mainList;
    private int index = -1;
    private ArrayAdapter<Album> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setTitle("My Albums");

        mainList = AlbumList.read(this);
        if(mainList == null){
            mainList = new AlbumList();
        }
        if(mainList.albList == null){
            mainList.albList = new ArrayList<Album>();
        }

        add = findViewById(R.id.add);
        delete = findViewById(R.id.delete);
        rename = findViewById(R.id.rename);
        open = findViewById(R.id.open);
        search = findViewById(R.id.search);
        albumList = findViewById(R.id.albList);
        adapter = new ArrayAdapter<Album>(this, R.layout.album, mainList.albList);
        albumList.setAdapter(adapter);
        albumList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        albumList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                index = position;
                albumList.setSelector(android.R.color.holo_orange_light);
                albumList.requestFocusFromTouch();
                albumList.setSelection(position);
                adapter.notifyDataSetChanged();
            }
        });
        onOpenClick();
        onAddClick();
    }

    protected void onAddClick(){
        add.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                dialog.setTitle("New Album");

                final EditText input = new EditText(MainActivity.this);
                dialog.setView(input);
                dialog.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String nameInput = input.getText().toString();
                        if(nameInput.trim().isEmpty()){
                            AlertDialog.Builder error = new AlertDialog.Builder(MainActivity.this);
                            error.setTitle("An album must be given a name.");
                            error.show();
                            return;
                        }
                        else{
                            for(Album alb : mainList.albList) {
                                if (nameInput.equals(alb.getName())) {
                                    AlertDialog.Builder error = new AlertDialog.Builder(MainActivity.this);
                                    error.setTitle("An album with this name already exists.");
                                    error.show();
                                    return;
                                }
                            }
                        }
                        mainList.albList.add(new Album(nameInput));
                        adapter.notifyDataSetChanged();
                        mainList.write(MainActivity.this);
                    }
                });
                dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                dialog.show();
            }
        });
    }

    protected void onOpenClick(){
        index = -1;
        open.setOnClickListener(new View.OnClickListener(){
           @Override
            public void onClick(View v){
                Intent startIntent = new Intent(MainActivity.this, OpenedAlbum.class);
               // startIntent.putExtra(ALBUM_ID, index);
                startActivity(startIntent);
            }

        });

    }
}