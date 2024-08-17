/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

/**
 *
 * @author Yogendra Rokaya
 */
import Model.DatabaseConnection;
import Model.LoginModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class LoginController {
    public boolean registerUser(LoginModel user) {
    Connection conn = DatabaseConnection.getConnection();
    if (conn != null) {
        try {
            // Check if the email already exists
            String checkEmailSql = "SELECT COUNT(*) FROM users WHERE email = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkEmailSql);
            checkStmt.setString(1, user.getEmail());
            ResultSet rs = checkStmt.executeQuery();
            rs.next();
            int count = rs.getInt(1);

            if (count > 0) {
                // Email already exists
                System.out.println("Email already in use. Please use a different email.");
                return false;
            } else {
                // Email does not exist, proceed with registration
                String sql = "INSERT INTO users (first_name, last_name, email, password) VALUES (?, ?, ?, ?)";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, user.getFirstName());
                stmt.setString(2, user.getLastName());
                stmt.setString(3, user.getEmail());
                stmt.setString(4, user.getPassword());
                stmt.executeUpdate();
                System.out.println("User registered successfully.");
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    } else {
        System.out.println("Database connection failed.");
        return false;
    }
}
    public boolean loginUser(String email, String password) {
        String sql = "SELECT * FROM users WHERE email = ? AND password = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, email);
            pstmt.setString(2, password);

            var rs = pstmt.executeQuery();
            return rs.next(); // Returns true if a record is found

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}


