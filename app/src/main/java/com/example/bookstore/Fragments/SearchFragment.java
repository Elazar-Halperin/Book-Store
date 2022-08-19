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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class SearchFragment extends Fragment {

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

//        if(savedInstanceState != null) return;
        booksList = new ArrayList<>();
        booksList.add(new BookModel("a;sldjfal", "Harry Potter", "harry potter is a bitch", Collections.EMPTY_LIST,"J.K Rolling" ,  322, 4.5, "71-++hbbERL._AC_SY679_.jpg"));
        booksList.add(new BookModel("a;sldjfal", "Harry Potter", "harry potter is a bitch", Collections.EMPTY_LIST,"J.K Rolling" ,  322, 4.5, "71-++hbbERL._AC_SY679_.jpg"));
        booksList.add(new BookModel("a;sldjfal", "Harry Potter", "harry potter is a bitch", Collections.EMPTY_LIST,"J.K Rolling" ,  322, 4.5, "71-++hbbERL._AC_SY679_.jpg"));
        booksList.add(new BookModel("a;sldjfal", "Harry Potter", "harry potter is a bitch", Collections.EMPTY_LIST,"J.K Rolling" ,  322, 4.5, "71-++hbbERL._AC_SY679_.jpg"));
        booksList.add(new BookModel("a;sldjfal", "Harry Potter", "harry potter is a bitch", Collections.EMPTY_LIST,"J.K Rolling" ,  322, 4.5, "71-++hbbERL._AC_SY679_.jpg"));
        rv_searchBooks = view.findViewById(R.id.rv_searchBook);
        layoutManager = new LinearLayoutManager(getActivity());
        adapter =  new BooksAdapter2(booksList, getActivity());
        rv_searchBooks.setLayoutManager(layoutManager);
        rv_searchBooks.setAdapter(adapter);
    }
}