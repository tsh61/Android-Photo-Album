package com.example.androidphotos27;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

public class Slideshow extends AppCompatActivity {

    public static final String ALBUM_ID = "albumID";
    public static final String PHOTO_ID = "photoID";

    private int albumID;
    private int photoID;
    //tags
    private int index = -1;
    private Button delete, addLocation, addPerson;
    private ListView tags;
    private ArrayAdapter<Tag> tagArrayAdapter;

    //thumbnails
    private Button next;
    private Button previous;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slideshow);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final ImageView img = findViewById(R.id.imageView);
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            albumID = bundle.getInt(ALBUM_ID);
            photoID = bundle.getInt(PHOTO_ID);
        }
        if(MainActivity.mainList.albList.get(albumID).getPhotos().get(photoID) != null){
            img.setImageBitmap(MainActivity.mainList.albList.get(albumID).getPhotos().get(photoID).getPic());
        }

        //add = findViewById(R.id.addTag);
        addLocation = findViewById(R.id.addLocation);
        addPerson = findViewById(R.id.addPerson);
        delete = findViewById(R.id.deleteTag);
        next = findViewById(R.id.nextPhoto);
        previous = findViewById(R.id.prevPhoto);
        tags = findViewById(R.id.tagList);
        tags.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        tagArrayAdapter = new ArrayAdapter<Tag>(Slideshow.this, R.layout.album, MainActivity.mainList.albList.get(albumID).getPhotos().get(photoID).getTags());
        tags.setAdapter(tagArrayAdapter);
        tags.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                index = position;
                tags.setSelector(android.R.color.holo_orange_light);
                tags.requestFocusFromTouch();
                tags.setSelection(position);
                tagArrayAdapter.notifyDataSetChanged();
            }
        });

        //onAddTag();
        onDeleteTag();
        onNext();
        onPrevious();
        onAddLocation();
        onAddPerson();
    }
    /*protected void onAddTag(){
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder msg = new AlertDialog.Builder(Slideshow.this);
                msg.setTitle("Enter EITHER a location or a person tag value.");
                LinearLayout lila = new LinearLayout(Slideshow.this);
                lila.setOrientation(LinearLayout.VERTICAL);
                final EditText input1 = new EditText(Slideshow.this);
                input1.setHint("Location");
                final EditText input2 = new EditText(Slideshow.this);
                input2.setHint("Person");
                lila.addView(input1);
                lila.addView(input2);
                msg.setView(lila);
                msg.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String inputText1 = input1.getText().toString();
                        String inputText2 = input2.getText().toString();
                        if(inputText1.trim().isEmpty() && inputText2.trim().isEmpty()){
                            AlertDialog.Builder error = new AlertDialog.Builder(Slideshow.this);
                            error.setMessage("Please enter tag information.");
                            error.show();
                            return;
                        }
                        else if(!inputText1.trim().isEmpty() && !inputText2.trim().isEmpty()){
                            AlertDialog.Builder error = new AlertDialog.Builder(Slideshow.this);
                            error.setMessage("Please enter ONLY one type of tag at a time.");
                            error.show();
                            return;
                        }
                        else{
                            if(!inputText1.isEmpty()){
                                MainActivity.mainList.albList.get(albumID).getPhotos().get(photoID).addTag("Location", inputText1);
                                tagArrayAdapter.notifyDataSetChanged();
                                MainActivity.mainList.write(Slideshow.this);

                            }
                            else{
                                MainActivity.mainList.albList.get(albumID).getPhotos().get(photoID).addTag("Person", inputText2);
                                tagArrayAdapter.notifyDataSetChanged();
                                MainActivity.mainList.write(Slideshow.this);

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
    }*/

    protected void onAddLocation(){
        addLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder msg = new AlertDialog.Builder(Slideshow.this);
                msg.setTitle("Enter a location tag.");
                final EditText input = new EditText(Slideshow.this);
                msg.setView(input);

                msg.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String inputString = input.getText().toString();
                        if(inputString.trim().isEmpty()){
                            AlertDialog.Builder error = new AlertDialog.Builder(Slideshow.this);
                            error.setMessage("Please enter the tag information.");
                            error.show();
                            return;
                        }
                        else{
                            for(Tag tag : MainActivity.mainList.albList.get(albumID).getPhotos().get(photoID).getTags()){
                                if(tag.getName().equals("Location") && tag.getValue().equals(inputString)){
                                    AlertDialog.Builder error = new AlertDialog.Builder(Slideshow.this);
                                    error.setMessage("A location tag with this name already exists for this photo.");
                                    error.show();
                                    return;
                                }
                            }
                            MainActivity.mainList.albList.get(albumID).getPhotos().get(photoID).addTag("Location", inputString);
                            tagArrayAdapter.notifyDataSetChanged();
                            MainActivity.mainList.write(Slideshow.this);
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

    protected void onAddPerson(){
        addPerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder msg = new AlertDialog.Builder(Slideshow.this);
                msg.setTitle("Enter a person tag.");
                final EditText input = new EditText(Slideshow.this);
                msg.setView(input);

                msg.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String inputString = input.getText().toString();
                        if(inputString.trim().isEmpty()){
                            AlertDialog.Builder error = new AlertDialog.Builder(Slideshow.this);
                            error.setMessage("Please enter the tag information.");
                            error.show();
                            return;
                        }
                        else{
                            for(Tag tag : MainActivity.mainList.albList.get(albumID).getPhotos().get(photoID).getTags()){
                                if(tag.getName().equals("Person") && tag.getValue().equals(inputString)){
                                    AlertDialog.Builder error = new AlertDialog.Builder(Slideshow.this);
                                    error.setMessage("A person tag with this name already exists for this photo.");
                                    error.show();
                                    return;
                                }
                            }
                            MainActivity.mainList.albList.get(albumID).getPhotos().get(photoID).addTag("Person", inputString);
                            tagArrayAdapter.notifyDataSetChanged();
                            MainActivity.mainList.write(Slideshow.this);
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

    protected void onDeleteTag(){
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(index == -1){
                    android.app.AlertDialog.Builder error = new android.app.AlertDialog.Builder(Slideshow.this);
                    error.setMessage("Please select a tag to delete.");
                    error.show();
                    return;
                }
                else{
                    AlertDialog.Builder msg = new AlertDialog.Builder(Slideshow.this);
                    msg.setTitle("Are you sure you want to delete this tag? This action is permanent.");

                    msg.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            MainActivity.mainList.albList.get(albumID).getPhotos().get(photoID).removeTag(index);
                            index = -1;
                            tagArrayAdapter.notifyDataSetChanged();
                            MainActivity.mainList.write(Slideshow.this);
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
    protected void onNext(){
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MainActivity.mainList.albList.get(albumID).getPhotos().size() <= photoID + 1){
                    return;
                }
                else{
                    Slideshow.super.finish();
                    Bundle bundle = new Bundle();
                    bundle.putInt(Slideshow.ALBUM_ID, albumID);
                    bundle.putInt(Slideshow.PHOTO_ID, photoID + 1);
                    Intent intent = new Intent(Slideshow.this, Slideshow.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });
    }
    protected void onPrevious(){
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(photoID - 1 < 0){
                    return;
                }
                Slideshow.super.finish();
                Bundle bundle = new Bundle();
                bundle.putInt(Slideshow.ALBUM_ID, albumID);
                bundle.putInt(Slideshow.PHOTO_ID, photoID - 1);
                Intent intent = new Intent(Slideshow.this, Slideshow.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
}