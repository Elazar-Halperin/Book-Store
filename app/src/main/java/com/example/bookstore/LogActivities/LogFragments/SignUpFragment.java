package com.example.bookstore.LogActivities.LogFragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.bookstore.HomeActivity;
import com.example.bookstore.LogActivities.AuthorLogActivity;
import com.example.bookstore.Models.UserModel;
import com.example.bookstore.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpFragment extends Fragment {

    FirebaseAuth mAuth;
    TextInputEditText et_userName, et_email, et_password;
    Button btn_signUp, btn_authorSignUp;
    FirebaseDatabase database;
    ProgressBar progressBar;
    DatabaseReference dbRef;


    public SignUpFragment() {
        // Required empty public constructor
        super(R.layout.fragment_sign_up);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        et_userName = view.findViewById(R.id.et_username);
        et_email = view.findViewById(R.id.et_emailSignUp);
        et_password = view.findViewById(R.id.et_passwordSignUp);
        btn_signUp = view.findViewById(R.id.btn_signUp);
        btn_authorSignUp = view.findViewById(R.id.btn_authorSignUp);
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        dbRef = database.getReference();
        progressBar = getActivity().findViewById(R.id.pb_log);

        et_userName.requestFocus();

        btn_authorSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), AuthorLogActivity.class);
                startActivity(i);
            }
        });

        btn_signUp.setOnClickListener(new View.OnClickListener() {
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
        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            UserModel user = new UserModel(username, email, false);
                            dbRef = database.getReference("users");
                            dbRef.child(mAuth.getCurrentUser().getUid())
                                    .setValue(user)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                progressBar.setVisibility(View.GONE);
                                                Toast.makeText(getActivity(), "Signed up successfully", Toast.LENGTH_SHORT).show();
                                                Intent i = new Intent(getActivity(), HomeActivity.class);
                                                startActivity(i);
                                                getActivity().finish();
                                            } else {
                                                Toast.makeText(getActivity(), "Failed to register! Try again!", Toast.LENGTH_SHORT).show();
                                                progressBar.setVisibility(View.GONE);
                                            }
                                        }
                                    });
                        } else {
                            Toast.makeText(getActivity(), "Failed to register! Try again!", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
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