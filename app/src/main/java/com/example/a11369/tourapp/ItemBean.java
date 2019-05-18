package com.example.a11369.tourapp;

public class ItemBean {
    private String title;
    private String author;
    private String text;
    private String address;
    public ItemBean(String newTitle,String newAuthor,String newText,String newAddress){
        this.title=newTitle;
        this.author=newAuthor;
        this.text=newText;
        this.address=newAddress;
    }

    public String getTitle(){
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
