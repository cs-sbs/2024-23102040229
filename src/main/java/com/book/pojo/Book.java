package com.book.pojo;

import java.time.LocalDate;

public class Book {
    private int id;
    private String title;
    private String author;
    private String isbn;

    public Book(int id, String title, String author, String isbn, String category, String publishedDate) {
    }

    public Book(int id, String title, String author, String isbn, String category, LocalDate publishedDate) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.category = category;
        this.publishedDate = publishedDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public LocalDate getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(LocalDate publishedDate) {
        this.publishedDate = publishedDate;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", isbn='" + isbn + '\'' +
                ", category='" + category + '\'' +
                ", publishedDate=" + publishedDate +
                '}';
    }

    private String category;
    private LocalDate publishedDate;

    // 构造函数、getter/setter省略...

    // 抽象方法，要求子类实现
}

// 子类 ComputerBook 示例

class ComputerBook extends Book {

    private String programmingLanguage;

    public ComputerBook(int id, String title, String author, String isbn, String category, String publishedDate) {
        super(id, title, author, isbn, category, publishedDate);
    }



    // 构造函数、getter/setter省略...
}