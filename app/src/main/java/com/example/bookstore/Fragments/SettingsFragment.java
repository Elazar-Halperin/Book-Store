package com.example.bookstore.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.bookstore.LogActivities.AuthorLogActivity;
import com.example.bookstore.LogActivities.ReaderLogActivity;
import com.example.bookstore.R;
import com.google.firebase.auth.FirebaseAuth;

public class SettingsFragment extends Fragment {

    Button btn_signOut;
    FirebaseAuth mAuth;

    public SettingsFragment() {
        // Required empty public constructor
        super(R.layout.fragment_settings);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btn_signOut = view.findViewById(R.id.btn_signOut);
        mAuth = FirebaseAuth.getInstance();

        btn_signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Intent i = new Intent(getActivity(), ReaderLogActivity.class);
                getActivity().finish();
                startActivity(i);
            }
        });
    }
}