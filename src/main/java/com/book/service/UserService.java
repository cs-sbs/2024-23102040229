package com.book.service;

import com.book.DatabaseUtil;
import com.book.dao.UserDAO;
import com.book.pojo.User;

import java.sql.Connection;
import java.sql.SQLException;

public class UserService {

    private final UserDAO userDAO = new UserDAO();

    public boolean register(String username, String password, User.Role role) {
        try (Connection connection = DatabaseUtil.getConnection()) {
            return userDAO.register(connection, username, password, role);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public void createAdminUser(String username, String password) {
        try (Connection connection = DatabaseUtil.getConnection()) {
            userDAO.register(connection, username, password, User.Role.ADMIN);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public User login(String username, String password) {
        try (Connection connection = DatabaseUtil.getConnection()) {
            return userDAO.login(connection, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}