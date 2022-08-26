package com.example.bookstore.ReyclerViewAdapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.AdaptiveIconDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.TransitionOptions;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory;
import com.example.bookstore.Models.BookModel;
import com.example.bookstore.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
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


        holder.getLl_bookHolder().setOnClickListener(view -> {

        });

        holder.getTv_bookName().setText(booksList.get(position).getBookName());

        StorageReference imgRef = FirebaseStorage.getInstance().getReference().child("book_images/space.jpg");
        Log.d("files", imgRef.getName());


        Glide.with(context)
                .load(booksList.get(position).getImageFileName())
                .placeholder(R.drawable.book_covers_big_2019101610)
                .into(holder.getIv_bookCover());

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

