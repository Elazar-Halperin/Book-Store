package com.example.bookstore;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.bookstore.Constants.Genres;
import com.example.bookstore.Models.BookModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

public class AddBookActivity extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference booksRef;
    StorageReference imagesRef;
    List<String> genreList;
    Uri selectedImage;

    TextInputEditText et_bookName, et_bookDesc, et_pageNum;
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
        imagesRef = FirebaseStorage.getInstance().getReference().child("book_images/");

        // Initialize the view.
        et_bookName = findViewById(R.id.et_bookName);
        et_bookDesc = findViewById(R.id.et_bookDesc);
        et_pageNum = findViewById(R.id.et_numberOfPages);
        btn_addBook = findViewById(R.id.btn_addBook);
        cg_choose = findViewById(R.id.cg_choose);
        cg_pickedGenres = findViewById(R.id.cg_pickedGenres);
        fab_openGallery = findViewById(R.id.fab_openGallery);
        iv_bookCover = findViewById(R.id.iv_cover);


        // Initialize other values.
        genreList = new ArrayList<>();


        // set Views listeners
        btn_addBook.setOnClickListener(v -> {
            if (!isValidInput()) return;

            addBook();
        });

        fab_openGallery.setOnClickListener(v -> {
            Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(i, 100);
        });

        // create list of genres(chips).
        createGenresChips();
    }

    // creates the book and then
    // it will return you to the home page.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null) {
            selectedImage = data.getData();
            iv_bookCover.setImageURI(selectedImage);
        }
    }

    private void addBook() {
        String name = et_bookName.getText().toString().trim();
        int pageNum = Integer.parseInt(et_pageNum.getText().toString().trim());
        String description = et_bookDesc.getText().toString().trim();

        StringBuilder builder = new StringBuilder();
        for (String genre : genreList) builder.append(genre).append(" ");
        // add it to single string
        String genres = builder.toString().trim();

        imagesRef.child("cover").putFile(selectedImage)
                .addOnCompleteListener(taskSnapshot -> {
                    String fileUri = taskSnapshot.getResult().getUploadSessionUri().toString();

                    String key = booksRef.push().getKey();

                    FirebaseDatabase.getInstance().getReference("users")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .child("name").get()
                            .addOnCompleteListener(task -> {
                                String authorName = task.getResult().getValue(String.class);
                                BookModel book = new BookModel(key, name, description, genres, authorName, pageNum, fileUri.toString());

                                booksRef.child(key)
                                        .setValue(book)
                                        .addOnCompleteListener(task1 -> finish());
                            });
                });
    }

    // the function will create all the genres chips
    // for later selection.
    private void createGenresChips() {
        List<String> genres = Genres.getListOfGenres(getApplicationContext());
        for (String genre : genres) {
            GenerateChip(genre);
        }

    }

    // gets String genre -
    // with that create a chip with genre text.
    // set onClick listener.
    // set onCloseClick listener.
    private void GenerateChip(String genre) {
        Chip chip = new Chip(AddBookActivity.this);
        chip.setText(genre);
        chip.setChipIcon(
                getResources().getDrawable(R.drawable.ic_launcher_foreground, getTheme())
        );
        chip.setChipIconVisible(false);
        chip.setClickable(true);
        // chip.setCheckable(true);
        chip.setOnClickListener(view -> {
            try {
                cg_choose.removeView(view);
                chip.setCloseIconVisible(true);
                cg_pickedGenres.addView(view);
                genreList.add(genre);
            } catch (Exception e) {

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

    // checks if all the values to create a book are valid.
    private boolean isValidInput() {
        String name = et_bookName.getText().toString().trim();
        String pages = et_bookName.getText().toString().trim();
        String description = et_bookDesc.getText().toString().trim();

        if (name.isEmpty()) {
            et_bookName.setError("Empty field");
            et_bookName.requestFocus();
            return false;
        }
        if (pages.isEmpty()) {
            et_pageNum.setError("Empty field");
            et_pageNum.requestFocus();
            return false;
        }
        if (description.isEmpty()) {
            et_bookDesc.setError("Please provide book's description!");
            et_bookDesc.requestFocus();
            return false;
        }

        try {
            int num = Integer.parseInt(et_pageNum.getText().toString());
        } catch (Exception ignored) {
            et_pageNum.setError("Bruh put numbers you fucking moron!");
            et_pageNum.requestFocus();
            return false;
        }

        if (genreList.isEmpty()) {
            cg_choose.requestFocus();
            Toast.makeText(getApplicationContext(), "Pls provide books genres", Toast.LENGTH_SHORT).show();
        }

        if (selectedImage == null) {
            fab_openGallery.requestFocus();
            Toast.makeText(getApplicationContext(), "Pls provide book's cover!", Toast.LENGTH_SHORT).show();
        }

        return true;
    }
}