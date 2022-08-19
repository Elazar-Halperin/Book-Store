package com.example.bookstore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.bookstore.LogActivities.ReaderLogActivity;
import com.google.firebase.auth.FirebaseAuth;

public class LauncherActivity extends AppCompatActivity {
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        mAuth = FirebaseAuth.getInstance();
        Intent i;
        if(mAuth.getCurrentUser() == null) {
             i = new Intent(getApplicationContext(), ReaderLogActivity.class);
        } else {
             i = new Intent(getApplicationContext(), HomeActivity.class);
        }
        startActivity(i);
        finish();
    }
}