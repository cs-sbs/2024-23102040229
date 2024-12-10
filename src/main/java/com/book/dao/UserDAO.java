package com.book.dao;

import com.book.pojo.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {

    public boolean register(Connection connection, String username, String password, User.Role role) throws SQLException {
        String sql = "INSERT INTO users (username, password, role) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, username);
            statement.setString(2, password); // 注意：这里应存储加密后的密码
            statement.setString(3, role.name());
            return statement.executeUpdate() > 0;
        }
    }

    public User login(Connection connection, String username, String password) throws SQLException {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, username);
            statement.setString(2, password);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    // 将role字段转换为大写，确保与枚举匹配
                    String roleStr = resultSet.getString("role").toUpperCase();
                    return new User(
                            resultSet.getInt("id"),
                            resultSet.getString("username"),
                            resultSet.getString("password"),
                            User.Role.valueOf(roleStr)
                    );
                }
            }
        }
        return null;
    }
}