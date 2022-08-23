package com.example.bookstore;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.bookstore.Constants.Genres;
import com.example.bookstore.Models.BookModel;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AddBookActivity extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference booksRef;
    StorageReference imagesRef;
    List<String> genreList;

    TextInputEditText et_bookName, et_pageNum;
    Button btn_addBook;
    FloatingActionButton fab_openGallery;
    ChipGroup cg_choose, cg_pickedGenres;
    ImageView iv_bookCover;

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
        cg_choose = findViewById(R.id.cg_choose);
        cg_pickedGenres = findViewById(R.id.cg_pickedGenres);
        fab_openGallery = findViewById(R.id.fab_openGallery);
        iv_bookCover = findViewById(R.id.iv_cover);


        // Initialize other values.
        genreList = new ArrayList<>();

        imagesRef = FirebaseStorage.getInstance().getReference().child("book_images");


        // set Views listeners
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

        fab_openGallery.setOnClickListener(v -> {
            Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(i, 100);
        });

        // create list of genres(chips).
        createGenresChips();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK && data != null){
            Uri selectedImage = data.getData();
            iv_bookCover.setImageURI(selectedImage);
        }
    }

    private void createGenresChips() {
        List<String> genres = Genres.getListOfGenres(getApplicationContext());
        for (String genre : genres) {
            GenerateChip(genre);
        }

    }

    private void GenerateChip(String genre) {
        Chip chip = new Chip(AddBookActivity.this);
        chip.setText(genre);
        chip.setChipIcon(
                getResources().getDrawable(R.drawable.ic_launcher_foreground, getTheme())
        );
        chip.setChipIconVisible(false);
        chip.setClickable(true);
        // chip.setCheckable(true);
        chip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    cg_choose.removeView(v);
                    chip.setCloseIconVisible(true);
                    cg_pickedGenres.addView(v);
                    genreList.add(genre);
                } catch (Exception e) {

                }
            }
        });
        chip.setOnCloseIconClickListener(view -> {
            cg_pickedGenres.removeView(view);
            chip.setCloseIconVisible(false);
            cg_choose.addView(view);
            chip.setChecked(true);
            chip.requestFocus();
            genreList.remove(genre);
        });

        cg_choose.addView(chip);
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