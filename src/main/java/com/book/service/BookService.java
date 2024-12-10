package com.book.service;

import com.book.DatabaseUtil;
import com.book.dao.BookDAO;
import com.book.pojo.Book;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookService {



    private final BookDAO bookDAO = new BookDAO();



    public List<Book> getAllBooks() {
        return bookDAO.getAllBooks();
    }

    public List<Book> getBooksByCategory(String category) {
        try (Connection conn = DatabaseUtil.getConnection()) {
            return bookDAO.getBooksByCategory(conn, category);
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>(); // 或者抛出异常、返回空列表等
        }
    }


    public List<Book> searchBooks(String keyword) {
        try (Connection connection = DatabaseUtil.getConnection()) {
            return bookDAO.searchBooks(connection, keyword);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean addBook(String title, String author, String isbn, String category, String publishedDateStr) {
        try (Connection connection = DatabaseUtil.getConnection()) {
            return bookDAO.addBook(connection, title, author, isbn, category, publishedDateStr);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteBook(String isbn) {
        try (Connection connection = DatabaseUtil.getConnection()) {
            return bookDAO.deleteBook(connection, isbn);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}