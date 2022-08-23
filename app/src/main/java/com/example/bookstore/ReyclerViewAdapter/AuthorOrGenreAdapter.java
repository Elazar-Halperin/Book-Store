package com.example.bookstore.ReyclerViewAdapter;

import android.content.Context;
import android.os.Handler;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookstore.Models.BookModel;
import com.example.bookstore.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AuthorOrGenreAdapter extends RecyclerView.Adapter<AuthorOrGenreAdapter.AuthorOrGenreViewHolder> {
    List<String> authorOrGenresList;
    Context context;
    Query booksRef;
    RecyclerView rv_books;
    Parcelable recyclerViewState;


    public AuthorOrGenreAdapter(List<String> authorOrGenre, Context context) {
        this.authorOrGenresList = authorOrGenre;
        this.context = context;

        booksRef = FirebaseDatabase.getInstance().getReference("books").orderByChild("bookName");
    }

    @NonNull
    @Override
    public AuthorOrGenreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.books_list_boxholder, parent, false);
        return new AuthorOrGenreViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull AuthorOrGenreViewHolder holder, int position) {

        holder.getTv_authorOrGenre().setText(authorOrGenresList.get(position));

        holder.getBtn_viewAll().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // go to other intent where where it will display all the books by this author
                // or display in other activity the genre
            }
        });

        List<BookModel> booksList = new ArrayList<>();
        rv_books = holder.getRv_books();

        LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        rv_books.setLayoutManager(layoutManager);

        RecyclerView.Adapter adapter = new BooksAdapter(booksList, context);
        rv_books.setAdapter(adapter);

        // Save state

        recyclerViewState = rv_books.getLayoutManager().onSaveInstanceState();

// Restore state
        rv_books.getLayoutManager().onRestoreInstanceState(recyclerViewState);



        booksRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                booksList.clear();
                for(DataSnapshot child : snapshot.getChildren()) {
                    BookModel book = child.getValue(BookModel.class);
                    if(book.getGenres().contains(authorOrGenresList.get(position)) && booksList.size() < 10) {
                        booksList.add(book);
                            Log.d("hello", "position: " + booksList.size());
                            adapter.notifyItemRangeChanged(0, adapter.getItemCount() - 1);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }





    @Override
    public int getItemCount() {
        return authorOrGenresList.size();
    }

    public class AuthorOrGenreViewHolder extends RecyclerView.ViewHolder {

        final Button btn_viewAll;
        final RecyclerView rv_books;
        final TextView tv_authorOrGenre;

        public AuthorOrGenreViewHolder(@NonNull View itemView) {
            super(itemView);

            btn_viewAll = itemView.findViewById(R.id.btn_viewAll);
            rv_books = itemView.findViewById(R.id.rv_books);
            tv_authorOrGenre = itemView.findViewById(R.id.tv_authorOrGenre);
        }

        public Button getBtn_viewAll() {
            return btn_viewAll;
        }

        public RecyclerView getRv_books() {
            return rv_books;
        }

        public TextView getTv_authorOrGenre() {
            return tv_authorOrGenre;
        }
    }
}
