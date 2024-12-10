package com.book.dao;

import com.book.DatabaseUtil;
import com.book.pojo.Book;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BookDAO {

    // 提交

    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM books";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                String author = rs.getString("author");
                String isbn = rs.getString("isbn");
                String category = rs.getString("category");
                LocalDate publishedDate = null;

                // 处理可能为空的日期字段
                if (rs.getObject("published_date") != null) {
                    publishedDate = rs.getDate("published_date").toLocalDate();
                }

                // 创建Book对象，并加入到列表中
                books.add(new Book(id, title, author, isbn, category, publishedDate));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return books;
    }



    public List<Book> getBooksByCategory(Connection connection, String category) throws SQLException {
        String sql = "SELECT * FROM books WHERE category = ?";
        List<Book> books = new ArrayList<>();

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            // 设置查询参数
            pstmt.setString(1, category);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String title = rs.getString("title");
                    String author = rs.getString("author");
                    String isbn = rs.getString("isbn");
                    LocalDate publishedDate = null;

                    // 处理可能为空的日期字段
                    if (rs.getObject("published_date") != null) {
                        publishedDate = rs.getDate("published_date").toLocalDate();
                    }

                    // 创建Book对象，并加入到列表中
                    books.add(new Book(id, title, author, isbn, category, publishedDate));
                }
            }
        }

        return books;
    }

    public List<Book> searchBooks(Connection connection, String keyword) throws SQLException {
        String sql = "SELECT * FROM books WHERE title LIKE ? OR isbn LIKE ?";
        List<Book> books = new ArrayList<>();

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            // 设置查询参数，注意使用%通配符来实现模糊匹配
            pstmt.setString(1, "%" + keyword + "%");
            pstmt.setString(2, "%" + keyword + "%");

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String title = rs.getString("title");
                    String author = rs.getString("author");
                    String isbn = rs.getString("isbn");
                    String category = rs.getString("category");
                    LocalDate publishedDate = null;

                    // 处理可能为空的日期字段
                    if (rs.getObject("published_date") != null) {
                        publishedDate = rs.getDate("published_date").toLocalDate();
                    }

                    // 创建Book对象，并加入到列表中
                    books.add(new Book(id, title, author, isbn, category, publishedDate));
                }
            }
        }

        return books;
    }


    public boolean addBook(Connection connection, String title, String author, String isbn, String category, String publishedDateStr) throws SQLException {
        String sql = "INSERT INTO books (title, author, isbn, category, published_date) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, title);
            statement.setString(2, author);
            statement.setString(3, isbn);
            statement.setString(4, category);
            statement.setString(5, publishedDateStr);
            return statement.executeUpdate() > 0;
        }
    }

    public boolean deleteBook(Connection connection, String isbn) throws SQLException {
        String sql = "DELETE FROM books WHERE isbn = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, isbn);
            return statement.executeUpdate() > 0;
        }
    }
}