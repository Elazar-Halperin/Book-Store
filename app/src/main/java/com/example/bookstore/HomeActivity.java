package com.example.bookstore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.example.bookstore.Models.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeActivity extends AppCompatActivity {

    BottomNavigationView bnv_homeNav;
    FloatingActionButton fab_addBook;

    NavController navController;
    FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference bookRef;
    DatabaseReference userRef;
    boolean isAuthor;
    UserModel user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // initialize the Firebase auth and database.
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        bookRef = database.getReference("books");
        userRef = database.getReference("users");
        bnv_homeNav = findViewById(R.id.bnv_homeNav);

        // initialize the views
        fab_addBook = findViewById(R.id.fab_addBook);
        fab_addBook.setVisibility(View.GONE);

        user = new UserModel();
        Log.d("database", "hello?");

        // Get the current signed user.
        userRef.child(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                user = snapshot.getValue(UserModel.class);
                isAuthor = user.isAuthor();

                Log.d("database", String.valueOf(isAuthor));
                // check if the user is an author.
                // if he does then show the add book button,
                // Add click listener to the add book button.
                if (isAuthor) {
                    fab_addBook.setVisibility(View.VISIBLE);
                    fab_addBook.setOnClickListener(view -> {
                        Intent i = new Intent(getApplicationContext(), AddBookActivity.class);
                        startActivity(i);
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        // Get the nav controller and the navHostFragment
        bnv_homeNav.setItemIconTintList(null);
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fcv_fragmentHomeHolder);
        navController = navHostFragment.getNavController();
    }

    @Override
    protected void onStart() {
        super.onStart();

        // Set the bottom navigation bor.
        NavigationUI.setupWithNavController(bnv_homeNav, navController);
    }
}