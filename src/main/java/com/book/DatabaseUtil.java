package com.book;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseUtil {
    private static final String URL = "jdbc:mysql://localhost:3306/BookManagementSystem?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root"; // 替换为你的MySQL用户名
    private static final String PASSWORD = "123456"; // 替换为你的MySQL密码

    static {
        try {
            // 加载MySQL JDBC驱动
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}