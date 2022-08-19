package com.example.bookstore.ReyclerViewAdapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.bookstore.Models.BookModel;
import com.example.bookstore.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.BooksViewHolder> {

    List<BookModel> booksList;
    Context context;

    public BooksAdapter(List<BookModel> booksList, Context context) {
        this.booksList = booksList;
        this.context = context;
    }

    @NonNull
    @Override
    public BooksViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.booksholder_box, parent, false);
        return new BooksViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BooksViewHolder holder, int position) {
        holder.getLl_bookHolder().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Go to book's activity where it will show all the info about the book
            }
        });

        holder.getTv_bookName().setText(booksList.get(position).getBookName());

//        StorageReference imgRef = FirebaseStorage.getInstance().getReferenceFromUrl("gs://book-store-c68f6.appspot.com").child("books_images/" + booksList.get(position).getImageFileName());
//        Glide.with(context)
//                .load(R.drawable.book_covers_big_2019101610)
//                .into(holder.getIv_bookCover());


    }

    @Override
    public int getItemCount() {
        return booksList.size();
    }

    static class BooksViewHolder extends RecyclerView.ViewHolder {
        final private LinearLayout ll_bookHolder;
        final private ImageView iv_bookCover;
        final private TextView tv_bookName;

        public BooksViewHolder(@NonNull View itemView) {
            super(itemView);

            ll_bookHolder = itemView.findViewById(R.id.ll_bookCard);
            iv_bookCover = itemView.findViewById(R.id.iv_bookCoverImage);
            tv_bookName = itemView.findViewById(R.id.tv_bookName);
        }

        public ImageView getIv_bookCover() {
            return iv_bookCover;
        }

        public LinearLayout getLl_bookHolder() {
            return ll_bookHolder;
        }

        public TextView getTv_bookName() {
            return tv_bookName;
        }
    }
}
