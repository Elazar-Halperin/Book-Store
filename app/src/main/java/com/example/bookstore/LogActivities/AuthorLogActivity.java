package com.example.bookstore.LogActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.bookstore.Constants.Genres;
import com.example.bookstore.HomeActivity;
import com.example.bookstore.Models.AuthorModel;
import com.example.bookstore.Models.BookModel;
import com.example.bookstore.Models.UserModel;
import com.example.bookstore.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AuthorLogActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    TextInputEditText et_userName, et_email, et_password;
    Button btn_authorSignUp;
    FirebaseDatabase database;
    LinearLayout pb_log;
    DatabaseReference dbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_author_log);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        et_email = findViewById(R.id.et_authorMail);
        et_userName = findViewById(R.id.et_authorName);
        et_password = findViewById(R.id.et_passwordAuthor);
        btn_authorSignUp = findViewById(R.id.btn_signAsAuthor);
        database = FirebaseDatabase.getInstance();
        pb_log = findViewById(R.id.pb_log2);
        dbRef = database.getReference();
        mAuth = FirebaseAuth.getInstance();

        btn_authorSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });

    }

    private void registerUser() {
        if (!isValidInput()) {
            return;
        }

        String username = et_userName.getText().toString().trim();
        String email = et_email.getText().toString().trim();
        String password = et_password.getText().toString().trim();
        pb_log.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            DatabaseReference databaseReference = database.getReference("authors");

                            List<BookModel> books = new ArrayList<>();
                            AuthorModel author = new AuthorModel(username, books);
                            databaseReference.child(mAuth.getCurrentUser().getUid())
                                    .setValue(author).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task1) {
                                            if (task1.isSuccessful()) {
                                                UserModel user = new UserModel(username, email, true);
                                                dbRef = database.getReference("users");
                                                dbRef.child(mAuth.getCurrentUser().getUid())
                                                        .setValue(user)
                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if (task.isSuccessful()) {
                                                                    pb_log.setVisibility(View.GONE);
                                                                    Toast.makeText(AuthorLogActivity.this, "Signed up successfully", Toast.LENGTH_SHORT).show();
                                                                    Intent i = new Intent(AuthorLogActivity.this, HomeActivity.class);
                                                                    startActivityForResult(i, 200);
                                                                    finish();
                                                                } else {
                                                                    Toast.makeText(AuthorLogActivity.this, "Failed to register! Try again!", Toast.LENGTH_SHORT).show();
                                                                    pb_log.setVisibility(View.GONE);
                                                                }
                                                            }
                                                        });
                                            } else {
                                                Toast.makeText(AuthorLogActivity.this, "Failed to register! Try again!", Toast.LENGTH_SHORT).show();
                                                pb_log.setVisibility(View.GONE);
                                            }
                                        }
                                    });


                        } else {
                            Toast.makeText(AuthorLogActivity.this, "Failed to register! Try again!", Toast.LENGTH_SHORT).show();
                            pb_log.setVisibility(View.GONE);
                        }
                    }
                });
    }


    private boolean isValidInput() {
        // getting all the text from the edit-texts and deleting all the spaces at the end and the start
        String username = et_userName.getText().toString().trim();
        String email = et_email.getText().toString().trim();
        String password = et_password.getText().toString().trim();
        if (username.isEmpty()) {
            et_userName.setError("Full name is required!");
            et_userName.requestFocus();
            return false;
        }
        if (email.isEmpty()) {
            et_email.setError("Email is required!");
            et_email.requestFocus();
            return false;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            et_email.setError("Please provide valid email");
            et_email.requestFocus();
            return false;
        }
        if (password.isEmpty()) {
            et_password.setError("Password is required!");
            et_password.requestFocus();
            return false;
        }
        if (password.length() < 6) {
            et_password.setError("Min password length is 6!");
            et_password.requestFocus();
            return false;
        }
        return true;
    }
}