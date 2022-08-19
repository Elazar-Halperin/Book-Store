package com.example.bookstore.Constants;

import android.content.Context;

import com.example.bookstore.MainActivity;
import com.example.bookstore.R;

import java.util.ArrayList;
import java.util.List;

public class Genres {
    public static final int FANTASY = R.string.fantasy;
    public static final int FOOD = R.string.food;
    public static final int HISTORY = R.string.history;
    public static final int MEMOIR = R.string.memoir;
    public static final int POLITICS = R.string.politics;
    public static final int YOUNG_ADULT = R.string.young_adult;
    public static final int CHILDREN_BOOKs = R.string.children_book;
    public static final int THRILLER = R.string.thriller;
    public static final int SCIENCE_FICTION = R.string.children_book;

    public static List<String> getListOfGenres(Context context) {

        List<String> list = new ArrayList<>();
        list.add(context.getResources().getString(FANTASY));
        list.add(context.getResources().getString(FOOD));
        list.add(context.getResources().getString(HISTORY));
        list.add(context.getResources().getString(MEMOIR));
        list.add(context.getResources().getString(POLITICS));
        list.add(context.getResources().getString(YOUNG_ADULT));
        list.add(context.getResources().getString(CHILDREN_BOOKs));
        list.add(context.getResources().getString(THRILLER));
        list.add(context.getResources().getString(SCIENCE_FICTION));

        return list;
    }

}
