package com.example.androidphotos27;

import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;

import java.io.File;
import java.util.Objects;

public class OpenedAlbum extends AppCompatActivity {

    public static final String ALBUM_ID = "albumID";
    private int albumID;
    private Button add, delete, display, move;
    private GridView thumbnails;
    private GridViewAdapter gridAdapter;
    private int index = -1;

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
        gridAdapter = new GridViewAdapter(OpenedAlbum.this, MainActivity.mainList.albList.get(albumID).getPhotos());
        thumbnails.setAdapter(gridAdapter);
        thumbnails.setChoiceMode(GridView.CHOICE_MODE_SINGLE);
        thumbnails.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                index = position;
                thumbnails.setSelector(android.R.color.holo_orange_light);
                thumbnails.requestFocusFromTouch();
                thumbnails.setSelection(position);
                gridAdapter.notifyDataSetChanged();
            }
        });
        onAddPhoto();
        onDeletePhoto();
        onMovePhoto();
        onDisplayPhoto();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent){
        super.onActivityResult(requestCode, resultCode, intent);
        if(resultCode == RESULT_OK){
            Uri image = intent.getData();
            ImageView img = new ImageView(this);
            img.setImageURI(image);
            BitmapDrawable draw = (BitmapDrawable) img.getDrawable();
            Bitmap selectedImg = draw.getBitmap();
            Photo pic = new Photo(selectedImg);

            File file = new File(Objects.requireNonNull(image.getPath()));

            String fName = "N/A";
            String[] col = {MediaStore.MediaColumns.DISPLAY_NAME};
            ContentResolver cr = getApplicationContext().getContentResolver();
            Cursor cur = cr.query(image, col, null, null, null);

            if(cur != null){
                try{
                    if(cur.moveToFirst()){
                        fName = cur.getString(0);
                    }
                }catch(Exception exception){

                }
            }
            pic.setFileName(fName);
            thumbnails.setAdapter(gridAdapter);
            gridAdapter.notifyDataSetChanged();
            MainActivity.mainList.albList.get(albumID).getPhotos().add(pic);
            MainActivity.mainList.write(OpenedAlbum.this);
            //gridAdapter.notifyDataSetChanged();
            //thumbnails.setAdapter(gridAdapter);
        }
    }

    protected void onAddPhoto(){
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 1);
            }
        });
    }
    public void onDeletePhoto(){
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder msg = new AlertDialog.Builder(OpenedAlbum.this);
                if(index == -1){
                    msg.setMessage("Please select a photo to delete.");
                    msg.show();
                }
                else{
                    msg.setTitle("Are you sure you want to delete this photo?");
                    //msg.show();
                    msg.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            index = -1;
                            MainActivity.mainList.albList.get(albumID).getPhotos().remove(index);
                            MainActivity.mainList.write(OpenedAlbum.this);
                            gridAdapter.notifyDataSetChanged();
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
    protected void onMovePhoto() {
        move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder msg = new AlertDialog.Builder(OpenedAlbum.this);
                AlertDialog.Builder error = new AlertDialog.Builder(OpenedAlbum.this);
                if (index == -1) {
                    msg.setMessage("Please select a photo to be moved.");
                    msg.show();
                    return;
                }
                msg.setTitle("Enter the name of the new album.");
                final EditText input = new EditText(OpenedAlbum.this);
                msg.setView(input);
                msg.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String nameInput = input.getText().toString();
                        if(nameInput.trim().isEmpty()){
                            error.setMessage("Please enter the album name.");
                            error.show();
                            return;
                        }
                        else {
                            boolean trigger = false;
                            for (Album alb : MainActivity.mainList.albList) {
                                if (nameInput.equals(alb.toString())) {
                                    trigger = true;
                                    alb.getPhotos().add(MainActivity.mainList.albList.get(albumID).getPhotos().get(index));
                                    MainActivity.mainList.albList.get(albumID).getPhotos().remove(index);
                                    MainActivity.mainList.write(OpenedAlbum.this);
                                    gridAdapter.notifyDataSetChanged();
                                }
                            }
                            if (trigger == false) {
                                error.setMessage("This album does not exist.");
                                error.show();
                                return;
                            }
                        }
                    }
                });
                msg.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                msg.show();
            }
        });
    }

    protected void onDisplayPhoto(){
        display.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(index == -1){
                    AlertDialog.Builder error = new AlertDialog.Builder(OpenedAlbum.this);
                    error.setMessage("Please select a photo to display.");
                    error.show();
                    return;
                }
                else{
                    Bundle bundle = new Bundle();
                    bundle.putInt(Slideshow.ALBUM_ID, albumID);
                    bundle.putInt(Slideshow.PHOTO_ID, index);
                    Intent intent = new Intent(OpenedAlbum.this, Slideshow.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });
    }
}