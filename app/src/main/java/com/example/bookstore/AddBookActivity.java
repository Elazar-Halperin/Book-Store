package com.example.bookstore;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

import com.example.bookstore.Constants.Genres;
import com.example.bookstore.Models.BookModel;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AddBookActivity extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference booksRef;
    List<String> genreList;

    TextInputEditText et_bookName, et_pageNum;
    Button btn_addBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        // Initialize the firebase projects features.
        database = FirebaseDatabase.getInstance();
        booksRef = database.getReference("books");

        // Initialize the view.
        et_bookName = findViewById(R.id.et_bookName);
        et_pageNum = findViewById(R.id.et_numberOfPages);
        btn_addBook = findViewById(R.id.btn_addBook);

        // Initialize other values.
        genreList = new ArrayList<>();

        genreList.add(getResources().getString(Genres.SCIENCE_FICTION));
        genreList.add(getResources().getString(Genres.FANTASY));

        btn_addBook.setOnClickListener(click -> {
            String key = booksRef.push().getKey();
            if (!isValidInput()) return;

            String name = et_bookName.getText().toString().trim();
            String pages = et_pageNum.getText().toString().trim();

            BookModel book = new BookModel();

            booksRef.setValue(new BookModel()).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    finish();
                }
            });

        });

    }

    private boolean isValidInput() {
        String bookName = et_bookName.getText().toString().trim();
        String pages = et_bookName.getText().toString().trim();

        if (bookName.isEmpty()) {
            et_bookName.setError("Empty field");
            et_bookName.requestFocus();
            return false;
        }
        if (pages.isEmpty()) {
            et_pageNum.setError("Empty field");
            et_pageNum.requestFocus();
            return false;
        }

        try {
            Integer.valueOf(pages);
        } catch (Exception ignored) {
            et_pageNum.setError("Bruh put numbers you fucking moron!");
            et_pageNum.requestFocus();
            return false;
        }

        return true;
    }

}