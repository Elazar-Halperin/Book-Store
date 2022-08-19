package com.example.bookstore.Models;

import java.util.ArrayList;
import java.util.List;

public class AuthorModel {
    String name;
    List<BookModel> books;

    public AuthorModel(String uid, String name, List<BookModel> books) {

        this.name = name;
        this.books = books;
    }

    public AuthorModel(String username, List<BookModel> books) {
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<BookModel> getBooks() {
        return books;
    }

    public void setBooks(List<BookModel> books) {
        this.books = books;
    }
}
