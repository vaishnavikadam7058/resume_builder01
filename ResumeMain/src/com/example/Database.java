package com.example;

import java.awt.image.BufferedImage;
import java.sql.*;

public class Database {
    private static final String URL = "jdbc:mysql://localhost:3306/resume_builder";
    private static final String USER = "root";
    private static final String PASSWORD = "root@123";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static boolean login(String username, String password) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String query = "SELECT * FROM users WHERE username = ? AND password = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean register(String username, String password, String email) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String query = "INSERT INTO users (username, password, email) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, password);
            statement.setString(3, email);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean resetPassword(String username, String newPassword) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String query = "UPDATE users SET password = ? WHERE username = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, newPassword);
            statement.setString(2, username);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }



    public static boolean resetPassword(String email) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String query = "SELECT * FROM users WHERE email = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String newPassword = "newPassword123"; // This should be a generated or user-inputted value
                query = "UPDATE users SET password = ? WHERE email = ?";
                statement = connection.prepareStatement(query);
                statement.setString(1, newPassword);
                statement.setString(2, email);
                return statement.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean saveProfile(String username, String firstName, String lastName, String phone, String address, String profile) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String query = "UPDATE users SET first_name = ?, last_name = ?, phone = ?, address = ?, profile = ? WHERE username = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, firstName);
            statement.setString(2, lastName);
            statement.setString(3, phone);
            statement.setString(4, address);
            statement.setString(5, profile);
            statement.setString(6, username);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static User loadProfile(String username) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String query = "SELECT * FROM users WHERE username = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String phone = resultSet.getString("phone");
                String address = resultSet.getString("address");
                String profile = resultSet.getString("profile");
                return new User(username, firstName, lastName, phone, address, profile);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

	public static boolean deleteProfile(String username) {
		// TODO Auto-generated method stub
		return false;
	}

	public static boolean saveProfile(String username, String firstName, String lastName, String phone, String email,
			String address, String profile, String education, String institution, String graduationYear,
			BufferedImage profileImage) {
		// TODO Auto-generated method stub
		return false;
	}
}

