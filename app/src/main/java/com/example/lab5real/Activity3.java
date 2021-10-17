package com.example.lab5real;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class Activity3 extends AppCompatActivity {

    int noteid = -1;

    public void onClick(View view){


    }

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
}