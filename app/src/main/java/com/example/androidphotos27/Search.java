package com.example.androidphotos27;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;
import android.widget.ListView;
import java.util.ArrayList;
import static com.example.androidphotos27.MainActivity.mainList;

public class Search extends AppCompatActivity {

private Button loc_search, per_search, and_search, or_search;
private EditText location, person;
private ArrayList<Photo> photos_arr =new ArrayList<>();
private ListView searchResults;
private GridViewAdapter imageAdapter;

@Override
protected void onCreate(Bundle savedInstanceState) {
super.onCreate(savedInstanceState);
setContentView(R.layout.activity_search);
Toolbar toolbar = findViewById(R.id.toolbar);
setSupportActionBar(toolbar);
getSupportActionBar().setDisplayHomeAsUpEnabled(true);

this.setTitle("Search");

searchResults=findViewById(R.id.searchResults);
location = findViewById(R.id.locationText);
person = findViewById(R.id.personText);
loc_search = findViewById(R.id.locationSearch);
per_search = findViewById(R.id.personSearch);
and_search = findViewById(R.id.andSearch);
or_search = findViewById(R.id.orSearch);

imageAdapter = new GridViewAdapter(this, photos_arr);
searchResults.setAdapter(imageAdapter);

location_search();
person_search();
person_or_loc();
person_and_loc();
}

protected void location_search() {
loc_search.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View view) {
photos_arr.clear();
imageAdapter.notifyDataSetChanged();

if(location.getText().toString().trim().isEmpty()){
androidx.appcompat.app.AlertDialog.Builder error = new androidx.appcompat.app.AlertDialog.Builder(Search.this);
error.setMessage("Please specify a location.");
error.show();
return;
}

Tag value = new Tag("Location", location.getText().toString());
test(value, "Location");

}
});
}

protected void person_search() {
per_search.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View view) {
photos_arr.clear();
imageAdapter.notifyDataSetChanged();

if(person.getText().toString().trim().isEmpty()){
androidx.appcompat.app.AlertDialog.Builder error = new androidx.appcompat.app.AlertDialog.Builder(Search.this);
error.setMessage("Please specify a person.");
error.show();
return;
}

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
Boolean temp1 = mainList.albList.get(i).getPhotos().get(j).getTags().get(k).equals(value);
Boolean temp2 = mainList.albList.get(i).getPhotos().get(j).getTags().get(k).toString().startsWith(value.toString());
if(temp1 || temp2) {

photos_arr.add(mainList.albList.get(i).getPhotos().get(j));
imageAdapter.notifyDataSetChanged();
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
photos_arr.clear();
imageAdapter.notifyDataSetChanged();

if(person.getText().toString().trim().isEmpty() || location.getText().toString().trim().isEmpty()){
androidx.appcompat.app.AlertDialog.Builder error = new androidx.appcompat.app.AlertDialog.Builder(Search.this);
error.setMessage("Please specify both a person and a location.");
error.show();
return;
}

Tag loc_value = new Tag("Location", location.getText().toString());
Tag per_value = new Tag("Person", person.getText().toString());

test2(loc_value, per_value, "or");

}
});
}

protected void person_and_loc() {
and_search.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View view) {

photos_arr.clear();
imageAdapter.notifyDataSetChanged();

if(person.getText().toString().trim().isEmpty() || location.getText().toString().trim().isEmpty()){
androidx.appcompat.app.AlertDialog.Builder error = new androidx.appcompat.app.AlertDialog.Builder(Search.this);
error.setMessage("Please specify both a person and location.");
error.show();
return;
}

Tag loc_value = new Tag("Location", location.getText().toString());
Tag per_value = new Tag("Person", person.getText().toString());

test2(loc_value, per_value, "and");


}
});
}

protected void test2(Tag loc_value, Tag per_value, String type){

int num_of_albums = mainList.albList.size();
int i = 0;
int check1 = 0;
int check2 = 0;

while(i<num_of_albums){
int num_of_photos = mainList.albList.get(i).getPhotos().size();
for(int j = 0; j < num_of_photos; j++) {
int num_of_tags = mainList.albList.get(i).getPhotos().get(j).getTags().size();
for (int k = 0; k < num_of_tags; k++) {

boolean temp2 = mainList.albList.get(i).getPhotos().get(j).getTags().get(k).toString().startsWith(loc_value.toString());
boolean temp4 = mainList.albList.get(i).getPhotos().get(j).getTags().get(k).toString().startsWith(per_value.toString());

if (type.equals("or")) {

if (temp2) {
photos_arr.add(mainList.albList.get(i).getPhotos().get(j));
imageAdapter.notifyDataSetChanged();
break;
}
if (temp4) {
photos_arr.add(mainList.albList.get(i).getPhotos().get(j));
imageAdapter.notifyDataSetChanged();
break;
}

} else if (type.equals("and")) {

if (temp2){
check1 = 1;
}
if (temp4){
check2 = 1;
}
if (check1 == 1 && check2 == 1) {
photos_arr.add(mainList.albList.get(i).getPhotos().get(j));
imageAdapter.notifyDataSetChanged();
}
}
}
}
i++;
}
}

        
}