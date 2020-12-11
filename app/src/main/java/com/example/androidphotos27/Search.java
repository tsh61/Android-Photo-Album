package com.example.androidphotos27;



import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.widget.Button;
import android.widget.EditText;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import static com.example.androidphotos27.MainActivity.mainList;

public class Search extends AppCompatActivity {

private Button loc_search, per_search, and_search, or_search;
private EditText location, person;
private ArrayList<Photo> photos_arr =new ArrayList<Photo>();

@Override
protected void onCreate(Bundle savedInstanceState) {
super.onCreate(savedInstanceState);
setContentView(R.layout.activity_search);
Toolbar toolbar = findViewById(R.id.toolbar);
setSupportActionBar(toolbar);
getSupportActionBar().setDisplayHomeAsUpEnabled(true);

this.setTitle("Search");

location = findViewById(R.id.locationText);
person = findViewById(R.id.personText);
loc_search = findViewById(R.id.locationSearch);
per_search = findViewById(R.id.personSearch);
and_search = findViewById(R.id.andSearch);
or_search = findViewById(R.id.orSearch);

location_search();
person_search();
person_or_loc();
person_and_loc();
}


protected void location_search() {
loc_search.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View view) {
//case when user types nothing = > Error message
if(location.getText().toString().trim().isEmpty()){
androidx.appcompat.app.AlertDialog.Builder error = new androidx.appcompat.app.AlertDialog.Builder(Search.this);
error.setMessage("Please specify a location.");
error.show();
return;
}

// String input = location.getText().toString();
Tag value = new Tag("Location", location.getText().toString());

test(value, "Location");
/* int num_of_albums = mainList.albList.size();
int i = 0;
while(i<num_of_albums){
int num_of_photos = mainList.albList.get(i).getPhotos().size();
for(int j = 0; j < num_of_photos; j++) {
int num_of_tags = mainList.albList.get(i).getPhotos().get(j).getTags().size();
for (int k = 0; k < num_of_tags; k++) {
  //  Boolean temp1 = mainList.albList.get(i).getPhotos().get(j).getTags().get(k).equals(value);
Boolean temp2 = mainList.albList.get(i).getPhotos().get(j).getTags().get(k).toString().startsWith("Location - "+ value);
   // if(temp1 || temp2) {
if(temp2) {
photos_arr.add(mainList.albList.get(i).getPhotos().get(j));
//picAdapter.notifyDataSetChanged();
}
}
}
i++;
}*/

}
});
}

protected void person_search() {
per_search.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View view) {
//case when user types nothing = > Error message
if(location.getText().toString().trim().isEmpty()){
AlertDialog.Builder error = new AlertDialog.Builder(Search.this);
error.setMessage("Please specify a person.");
error.show();
return;
}
//  String input = person.getText().toString();
Tag value = new Tag("Person", person.getText().toString());

test(value, "Person");

}
});
}
protected void test(Tag value, String type){

int num_of_albums = mainList.albList.size();
int i = 0;

while(i<num_of_albums){
int num_of_photos = mainList.albList.get(i).getPhotos().size();
for(int j = 0; j < num_of_photos; j++) {
int num_of_tags = mainList.albList.get(i).getPhotos().get(j).getTags().size();
for (int k = 0; k < num_of_tags; k++) {
//  Boolean temp1 = mainList.albList.get(i).getPhotos().get(j).getTags().get(k).equals(value);
Boolean temp2 = mainList.albList.get(i).getPhotos().get(j).getTags().get(k).toString().startsWith(type + " - " + value);
// if(temp1 || temp2) {
if(temp2) {
photos_arr.add(mainList.albList.get(i).getPhotos().get(j));
//picAdapter.notifyDataSetChanged();
}
}
}
i++;
}
}

protected void person_or_loc() {
or_search.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View view) {
//case when user types nothing = > Error message
if(person.getText().toString().trim().isEmpty() || location.getText().toString().trim().isEmpty()){
androidx.appcompat.app.AlertDialog.Builder error = new androidx.appcompat.app.AlertDialog.Builder(Search.this);
error.setMessage("Please specify both, a person and location.");
error.show();
return;
}
//  String input1 = location.getText().toString();
Tag loc_value = new Tag("Location", location.getText().toString());
//String input2 = person.getText().toString();
Tag per_value = new Tag("Person", person.getText().toString());

test2(loc_value, per_value, "or");

}
});
}

protected void person_and_loc() {
and_search.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View view) {
//case when user types nothing = > Error message
if(person.getText().toString().trim().isEmpty() || location.getText().toString().trim().isEmpty()){
androidx.appcompat.app.AlertDialog.Builder error = new androidx.appcompat.app.AlertDialog.Builder(Search.this);
error.setMessage("Please specify both, a person and location.");
error.show();
return;
}
//  String input1 = location.getText().toString();
Tag loc_value = new Tag("Location", location.getText().toString());
//String input2 = person.getText().toString();
Tag per_value = new Tag("Person", person.getText().toString());

test2(loc_value, per_value, "and");


}
});
}

protected void test2(Tag loc_value, Tag per_value, String type){

int num_of_albums = mainList.albList.size();
int i = 0;

while(i<num_of_albums){
int num_of_photos = mainList.albList.get(i).getPhotos().size();
for(int j = 0; j < num_of_photos; j++) {
int num_of_tags = mainList.albList.get(i).getPhotos().get(j).getTags().size();
for (int k = 0; k < num_of_tags; k++) {

//  Boolean temp1 = mainList.albList.get(i).getPhotos().get(j).getTags().get(k).equals(loc_value);
Boolean temp2 = mainList.albList.get(i).getPhotos().get(j).getTags().get(k).toString().startsWith(loc_value.toString());
// Boolean temp3 = mainList.albList.get(i).getPhotos().get(j).getTags().get(k).equals(per_value);
Boolean temp4 = mainList.albList.get(i).getPhotos().get(j).getTags().get(k).toString().startsWith(per_value.toString());

if(type == "or") {

if (temp2) {
photos_arr.add(mainList.albList.get(i).getPhotos().get(j));
//picAdapter.notifyDataSetChanged();
break;
}
if (temp4) {
photos_arr.add(mainList.albList.get(i).getPhotos().get(j));
//picAdapter.notifyDataSetChanged();
break;
}
}else if(type == "and"){

if(temp2 && temp4){
photos_arr.add(mainList.albList.get(i).getPhotos().get(j));
//picAdapter.notifyDataSetChanged();
}
}
}
}
i++;
}
}


}