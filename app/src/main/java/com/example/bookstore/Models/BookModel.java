package com.example.bookstore.Models;

import java.util.List;

public class BookModel {
    String bookName;
    String description;
    String authorUid;
    List<String> genreList;
    int numberOfPages;
    double bookRating;
    String imageFileName;
    float rating;
    int numberOfBookmarked;

    public BookModel() {
    }

    public BookModel(String bookName, String description, List<String> genreList , String authorUid, int numberOfPages, double bookRating, String imageFileName) {
        this.bookName = bookName;
        this.description = description;
        this.genreList = genreList;
        this.authorUid = authorUid;
        this.numberOfPages = numberOfPages;
        this.bookRating = bookRating;
        this.imageFileName = imageFileName;
        this.rating = 0;
        this.numberOfBookmarked = 0;
    }

    public List<String> getGenreList() {
        return genreList;
    }

    public void setGenreList(List<String> genreList) {
        this.genreList = genreList;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public int getNumberOfBookmarked() {
        return numberOfBookmarked;
    }

    public void setNumberOfBookmarked(int numberOfBookmarked) {
        this.numberOfBookmarked = numberOfBookmarked;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthorUid() {
        return authorUid;
    }

    public void setAuthorUid(String authorUid) {
        this.authorUid = authorUid;
    }

    public int getNumberOfPages() {
        return numberOfPages;
    }

    public void setNumberOfPages(int numberOfPages) {
        this.numberOfPages = numberOfPages;
    }

    public double getBookRating() {
        return bookRating;
    }

    public void setBookRating(double bookRating) {
        this.bookRating = bookRating;
    }

    public String getImageFileName() {
        return imageFileName;
    }

    public void setImageFileName(String imageFileName) {
        this.imageFileName = imageFileName;
    }
}
