package com.example.lab5real;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class LoginPage extends AppCompatActivity {

    String usernameEntry;
    TextView loginTextDisplay;
    public static ArrayList<Note> notes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.v("INFO", "Made it to LoginPage onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        // Display welcome message
        SharedPreferences sharedPreferences = getSharedPreferences("com.example.lab5real", Context.MODE_PRIVATE);
        String usernameEntry = sharedPreferences.getString("username", "");
        Log.v("INFO", "THIS IS WHAT USER IS DISPLAYED AS: " + usernameEntry);
        loginTextDisplay = findViewById(R.id.userDisplay);
        loginTextDisplay.setText("Welcome " + usernameEntry + "!");

        //get sqLiteDatabase instance
        Context context = getApplicationContext();
        SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase("notes", Context.MODE_PRIVATE,null);

        //initialize notes class variable using readNote()
        DBHelper helper = new DBHelper(sqLiteDatabase);
        notes = helper.readNotes(usernameEntry);

        ArrayList<String> displayNotes = new ArrayList<>();
        for(Note note : notes){
            displayNotes.add(String.format("Title:%s\nDate:%s", note.getTitle(), note.getDate()));
        }
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, displayNotes);
        ListView listView = (ListView) findViewById(R.id.notesListView);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                Intent intent = new Intent(getApplicationContext(), Activity3.class);
                intent.putExtra("noteid", position);
                startActivity(intent);
            }
        });

    }

    @Override
    /**
     * Solution taken from https://stackoverflow.com/questions/35648913/how-to-set-menu-to-toolbar-in-android/49098384
     * from user Keyur Lakhani
     */
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        super.onOptionsItemSelected(item);
        if (item.getItemId() == R.id.logout){
            Log.v("INFO", "Logout selected");
            Intent intent = new Intent(this, MainActivity.class);
            SharedPreferences sharedPreferences = getSharedPreferences("com.example.lab5real", Context.MODE_PRIVATE);
            sharedPreferences.edit().remove(MainActivity.userKey).apply();
            startActivity(intent);
            return true;
        }
        if (item.getItemId() == R.id.note){
            Intent intent = new Intent(this, Activity3.class);
            startActivity(intent);
        }
        return true;
    }

}