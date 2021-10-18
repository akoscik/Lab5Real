package com.example.lab5real;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    final public static String userKey = "username";

    public void onSubmit(View view){
        EditText userName = (EditText) findViewById(R.id.userText);
        String str = userName.getText().toString();
        switchActivities(str);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v("INFO", "main onCreate executed");

        //check to see if user is already logged in
        SharedPreferences sharedPreferences = getSharedPreferences("com.example.lab5real", Context.MODE_PRIVATE);
        // if the username is already entered:
        Log.v("INFO", "This is what is stored for shared pref before if statement: " + sharedPreferences.getString(userKey,""));
        if (sharedPreferences.getString(userKey, "").equals("")){
            Log.v("INFO", "if statement was executed");
            setContentView(R.layout.activity_main);
        } else{
            Log.v("INFO", "else statement for sharedPreferences executed");
            //get what the username is
            String usernameEntry = sharedPreferences.getString(userKey,"");
            //send the app to the login page with an intent
            Intent intent = new Intent(this, LoginPage.class);
            intent.putExtra("message", usernameEntry);
            startActivity(intent);
        }
    }

    public void switchActivities(String s){
        //set up intent and the extra message
        Log.v("INFO", "submit button pushed");
        Intent intent = new Intent(this, LoginPage.class);
        intent.putExtra("message", s);

        //saving the login info
        SharedPreferences sharedPreferences = getSharedPreferences("com.example.lab5real", Context.MODE_PRIVATE);
        sharedPreferences.edit().putString(MainActivity.userKey, s).apply();
        Log.v("INFO", "This is what the username entered was: " + s);
        // Actual switching activity part
        startActivity(intent);
    }

}