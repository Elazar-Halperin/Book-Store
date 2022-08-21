package com.example.bookstore.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.example.bookstore.Models.BookModel;
import com.example.bookstore.R;
import com.example.bookstore.ReyclerViewAdapter.BooksAdapter;
import com.example.bookstore.ReyclerViewAdapter.BooksAdapter2;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class SearchFragment extends Fragment {

    Query booksRef;

    RecyclerView rv_searchBooks;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;
    List<BookModel> booksList;

    public SearchFragment() {
        // Required empty public constructor

    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        booksList = new ArrayList<>();

        booksRef = FirebaseDatabase.getInstance().getReference("books").limitToFirst(10);

//        if(savedInstanceState != null) return;

        rv_searchBooks = view.findViewById(R.id.rv_searchBook);
        layoutManager = new LinearLayoutManager(getActivity());
        adapter =  new BooksAdapter2(booksList, getActivity());
        rv_searchBooks.setLayoutManager(layoutManager);
        rv_searchBooks.setAdapter(adapter);

        booksRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot child : snapshot.getChildren()) {
                    booksList.add(child.getValue(BookModel.class));
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}