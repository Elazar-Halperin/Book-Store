package com.example.bookstore.Models;

import java.util.List;

public class UserModel {

    String name;
    String email;
    boolean isAuthor;

    public UserModel(String name, String email, boolean isAuthor) {
        this.name = name;
        this.email = email;
        this.isAuthor = isAuthor;
    }

    public UserModel() {
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isAuthor() {
        return isAuthor;
    }

    public void setAuthor(boolean author) {
        isAuthor = author;
    }
}
