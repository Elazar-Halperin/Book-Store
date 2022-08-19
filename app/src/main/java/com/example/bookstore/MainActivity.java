package com.example.bookstore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.ActivityOptions;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    FirebaseStorage storage;
    StorageReference storageRef;
    StorageReference booksRef;
    FirebaseAuth mAuth;
    ConstraintLayout cl_holder;
    CardView cv_user;
    CardView cv_image;
    TextView tv_name;
    TextView tv_description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cv_user = findViewById(R.id.cv_user);
        cv_image = findViewById(R.id.cv_image);
        tv_name = findViewById(R.id.tv_name);
        tv_description = findViewById(R.id.tv_description);
        cl_holder = findViewById(R.id.cl_holder);


        cv_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("hello", "hello");


                        Intent i = new Intent(MainActivity.this, HomeActivity.class);
                        Pair[] pairs = new Pair[3];
                        pairs[0] = (new Pair<View, String>(cv_image, "imageTransition"));
                        pairs[1] = (new Pair<View, String>(tv_name, "nameTransition"));
                        pairs[2] = (new Pair<View, String>(tv_description, "descriptionTransition"));
                        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this,
                                pairs);
                        startActivity(i, options.toBundle());
            }
        });

    }
}