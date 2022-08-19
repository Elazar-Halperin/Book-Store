package com.example.bookstore.ReyclerViewAdapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookstore.Models.BookModel;
import com.example.bookstore.R;

import java.util.List;

public class BooksAdapter2 extends RecyclerView.Adapter<BooksAdapter2.BooksViewHolder> {

    List<BookModel> booksList;
    Context context;

    public BooksAdapter2(List<BookModel> booksList, Context context) {
        this.booksList = booksList;
        this.context = context;
    }

    @NonNull
    @Override
    public BooksViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.booksholder_box2, parent, false);
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
        holder.getTv_authorName().setText(booksList.get(position).getAuthorUid());
        holder.getRb_bookRating().setRating(booksList.get(position).getRating());
        int bookmarks = booksList.get(position).getNumberOfBookmarked();
        if(bookmarks > 999)
            holder.getTv_numberOfBookmarks().setText(((float)bookmarks) / 1000 + "k");
        else
            holder.getTv_numberOfBookmarks().setText(bookmarks+ "k");
        holder.getTv_numberOfPages().setText(booksList.get(position).getNumberOfPages() + "p");

    }

    @Override
    public int getItemCount() {
        return booksList.size();
    }

    static class BooksViewHolder extends RecyclerView.ViewHolder {
        final private LinearLayout ll_bookHolder;
        final private ImageView iv_bookCover;
        final private TextView tv_bookName;
        final private TextView tv_authorName;
        final private TextView tv_numberOfPages;
        final private TextView tv_numberOfBookmarks;
        final private RatingBar rb_bookRating;

        public BooksViewHolder(@NonNull View itemView) {
            super(itemView);

            ll_bookHolder = itemView.findViewById(R.id.ll_bookCard2);
            iv_bookCover = itemView.findViewById(R.id.img_bookCover);
            tv_bookName = itemView.findViewById(R.id.tv_bookName2);
            tv_authorName = itemView.findViewById(R.id.tv_authorName);
            tv_numberOfPages = itemView.findViewById(R.id.tv_numberOfPages);
            tv_numberOfBookmarks = itemView.findViewById(R.id.tv_numberOfBookmarks);
            rb_bookRating = itemView.findViewById(R.id.rb_bookRating);

        }


        public LinearLayout getLl_bookHolder() {
            return ll_bookHolder;
        }

        public TextView getTv_bookName() {
            return tv_bookName;
        }

        public TextView getTv_authorName() {
            return tv_authorName;
        }

        public ImageView getIv_bookCover() {
            return iv_bookCover;
        }

        public RatingBar getRb_bookRating() {
            return rb_bookRating;
        }

        public TextView getTv_numberOfBookmarks() {
            return tv_numberOfBookmarks;
        }

        public TextView getTv_numberOfPages() {
            return tv_numberOfPages;
        }
    }
}
