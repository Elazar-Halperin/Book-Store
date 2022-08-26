package com.example.bookstore.Models;

public class BookModel {
    String bookUid;
    String bookName;
    String description;
    String authorName;
    String genres;
    int numberOfPages;
    double bookRating;
    String imageFileName;
    float rating;
    int numberOfBookmarked;

    public BookModel() {
    }

    public BookModel(String bookUid, String bookName, String description, String genres, String authorName, int numberOfPages, String imageFileName) {
        this.bookUid = bookUid;
        this.bookName = bookName;
        this.description = description;
        this.genres = genres;
        this.authorName = authorName;
        this.numberOfPages = numberOfPages;
        this.bookRating = bookRating;
        this.imageFileName = imageFileName;
        this.rating = 0;
        this.numberOfBookmarked = 0;
    }

    public String getGenres() {
        return genres;
    }

    public void setGenreList(String genre) {
        this.genres = genre;
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

    public String getBookUid() {
        return bookUid;
    }


    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
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
