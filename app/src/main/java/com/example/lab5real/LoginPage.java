package com.example.lab5real;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

public class LoginPage extends AppCompatActivity {

    String usernameEntry;
    TextView loginTextDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.v("INFO", "Made it to LoginPage onCreate()");
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login_page);

        Intent intent = getIntent();
        usernameEntry = intent.getStringExtra("message");
        loginTextDisplay = findViewById(R.id.userDisplay);
        loginTextDisplay.setText("Welcome " + usernameEntry + "!");

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
        return true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        String s = getSharedPreferences("com.example.lab5real", Context.MODE_PRIVATE).getString(MainActivity.userKey, "");
        SharedPreferences sharedPreferences = getSharedPreferences("com.example.Lab5Real", Context.MODE_PRIVATE);
        sharedPreferences.edit().putString(MainActivity.userKey, s).apply();
    }
}