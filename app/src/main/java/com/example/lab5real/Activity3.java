package com.example.lab5real;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Activity3 extends AppCompatActivity {

    int noteid = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_3);

        EditText textBox = findViewById(R.id.textInputEditText);
        Intent intent = getIntent();
        noteid = intent.getIntExtra("noteid", -1);
        if (noteid != -1){
            Note note = LoginPage.notes.get(noteid);
            String noteContent = note.getContent();
            // use editText.setText() to display note content on the screen
            textBox.setText(noteContent);
        }
    }

    public void onClick(View view){
        // get the content from the box
        EditText contentEntry = findViewById(R.id.textInputEditText);
        String content = contentEntry.getText().toString();
        Log.v("INFO", content);
        // set up the database and the dbhelper
        Context context = getApplicationContext();
        SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase("notes", Context.MODE_PRIVATE,null);
        DBHelper helper = new DBHelper(sqLiteDatabase);
        //get the username
        SharedPreferences sharedPreferences = getSharedPreferences("com.example.lab5real", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username","");
        Log.v("INFO", "current username: " + username);
        //set up variables
        String title;
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        String date = dateFormat.format(new Date());
        //first case is if adding new note
        if (noteid == -1){
            title = "NOTE_" + (LoginPage.notes.size() +1);
            //call DBHelper function to save the new note
            helper.saveNotes(username,title,content,date);
        } else {
            title = "NOTE_" + (noteid +1);
            helper.updateNote(title,date,content,username);
        }
        //go back to the logged in page
        Intent intent = new Intent(this, LoginPage.class);
        startActivity(intent);

    }
}