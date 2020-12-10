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
        onDeleteClick();
        onRenameClick();
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
                            error.setMessage("An album must be given a name.");
                            error.show();
                            return;
                        }
                        else{
                            for(Album alb : mainList.albList) {
                                if (nameInput.equals(alb.getName())) {
                                    AlertDialog.Builder error = new AlertDialog.Builder(MainActivity.this);
                                    error.setMessage("An album with this name already exists.");
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
        open.setOnClickListener(new View.OnClickListener(){
           @Override
            public void onClick(View v){
               if(index == -1){
                   AlertDialog.Builder error = new AlertDialog.Builder(MainActivity.this);
                   error.setMessage("Please select an album to open.");
                   error.show();
                   return;
               }
               Bundle bundle = new Bundle();
               bundle.putInt(OpenedAlbum.ALBUM_ID, index);
               Intent intent = new Intent(MainActivity.this, OpenedAlbum.class);
               intent.putExtras(bundle);
               startActivity(intent);
            }

        });

    }
    protected void onDeleteClick(){
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(index == -1){
                    AlertDialog.Builder error = new AlertDialog.Builder(MainActivity.this);
                    error.setMessage("Please select an album to be deleted.");
                    error.show();
                    return;
                }
                else{
                    AlertDialog.Builder msg = new AlertDialog.Builder(MainActivity.this);
                    msg.setMessage("Are you sure you want to delete " + mainList.albList.get(index).toString() + "? This action is permanent.");
                    msg.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mainList.albList.remove(index);
                            index = -1;
                            adapter.notifyDataSetChanged();
                            mainList.write(MainActivity.this);
                        }
                    });
                    msg.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    msg.show();
                }
            }
        });
    }
    protected void onRenameClick(){
        rename.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(index == -1){
                    AlertDialog.Builder error = new AlertDialog.Builder(MainActivity.this);
                    error.setMessage("Please select an album to be renamed.");
                    error.show();
                    return;
                }
                else{
                    AlertDialog.Builder edit = new AlertDialog.Builder(MainActivity.this);
                    edit.setTitle("Enter a new name for the album.");
                    final EditText input = new EditText(MainActivity.this);
                    edit.setView(input);
                    edit.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String editInput = input.getText().toString();
                            if(editInput.trim().isEmpty()){
                                AlertDialog.Builder error = new AlertDialog.Builder(MainActivity.this);
                                error.setMessage("The album must be given a name.");
                                error.show();
                                return;
                            }
                            else{
                                for(Album alb : mainList.albList){
                                    if(editInput.equals(alb.getName())){
                                        AlertDialog.Builder error = new AlertDialog.Builder(MainActivity.this);
                                        error.setMessage("An album with this name already exists.");
                                        error.show();
                                        return;
                                    }
                                }
                            }
                            mainList.albList.get(index).setName(editInput);
                            adapter.notifyDataSetChanged();
                            mainList.write(MainActivity.this);
                        }
                    });
                    edit.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    edit.show();
                }
            }
        });
    }
}