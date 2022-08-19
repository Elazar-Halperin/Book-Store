package com.example.bookstore.Fragments;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.bookstore.Constants.Genres;
import com.example.bookstore.Models.UserModel;
import com.example.bookstore.R;
import com.example.bookstore.ReyclerViewAdapter.AuthorOrGenreAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class HomeFragment extends Fragment {

    RecyclerView rv_authorAndGenre;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;
    TextView tv_time, tv_userName;
    LinearLayout ll_homeLoad;

    FirebaseDatabase database;
    FirebaseAuth mAuth;
    DatabaseReference userRef;
    DatabaseReference bookRef;

    Parcelable mListState;
    List<String> genreAndAuthorList;
    String SAVED_RECYCLER_VIEW_DATASET_ID = "genre list";
    String SAVED_RECYCLER_VIEW_STATUS_ID = "rv id position";
    UserModel user;
    Bundle mSavedInstanceState;


    public HomeFragment() {
        // Required empty public constructor
        super(R.layout.fragment_home);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        genreAndAuthorList = new ArrayList<>(Genres.getListOfGenres(getActivity()));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(savedInstanceState != null) {
            restorePreviousState();
            Log.d("saveInstance", "Saved? ");
        } else {

//            Collections.shuffle(genreAndAuthorList);

            rv_authorAndGenre = view.findViewById(R.id.rv_authorAndGenre);
            layoutManager = new LinearLayoutManager(getActivity());
            adapter = new AuthorOrGenreAdapter(genreAndAuthorList, getActivity());

            database = FirebaseDatabase.getInstance();
            userRef = database.getReference("users");
            bookRef = database.getReference("books");
            mAuth = FirebaseAuth.getInstance();

            rv_authorAndGenre.setLayoutManager(layoutManager);
            rv_authorAndGenre.setAdapter(adapter);

            tv_time = view.findViewById(R.id.tv_helloToUser);
            tv_userName = view.findViewById(R.id.tv_card_userName);
            ll_homeLoad = view.findViewById(R.id.ll_load);

            ll_homeLoad.setVisibility(View.VISIBLE);
            tv_time.setText(parseTimeIntoString());


            userRef.child(mAuth.getCurrentUser().getUid()).get()
                    .addOnCompleteListener(task -> {
                        if(task.isSuccessful()) {
                            user = task.getResult().getValue(UserModel.class);
                            tv_userName.setText(user.getName());
                            ll_homeLoad.setVisibility(View.GONE);
                        }
                    });
        }
    }

    private String parseTimeIntoString() {
        Calendar c = Calendar.getInstance();
        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);

        if (timeOfDay >= 0 && timeOfDay < 12)
            return getActivity().getResources().getString(R.string.good_morning);
        if (timeOfDay >= 12 && timeOfDay < 18)
            return getActivity().getResources().getString(R.string.good_afternoon);
        if (timeOfDay >= 18 && timeOfDay < 21)
            return getActivity().getResources().getString(R.string.good_evening);
        return getActivity().getResources().getString(R.string.good_night);
    }


    // save fragment state


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        Parcelable listState = rv_authorAndGenre.getLayoutManager().onSaveInstanceState();
        // put recyclerView position
        outState.putParcelable(SAVED_RECYCLER_VIEW_STATUS_ID, listState);
        // put recyclerView items
        outState.putStringArrayList(SAVED_RECYCLER_VIEW_DATASET_ID,(ArrayList<String>) genreAndAuthorList);
        super.onSaveInstanceState(outState);

    }

    private void restorePreviousState() {
        mListState = mSavedInstanceState.getParcelable(SAVED_RECYCLER_VIEW_STATUS_ID);

        genreAndAuthorList = mSavedInstanceState.getStringArrayList(SAVED_RECYCLER_VIEW_DATASET_ID);

        adapter = new AuthorOrGenreAdapter(genreAndAuthorList, getActivity());

        rv_authorAndGenre.getLayoutManager().onRestoreInstanceState(mListState);
    }



}