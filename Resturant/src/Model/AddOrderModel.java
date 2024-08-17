/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author Yogendra Rokaya
 */
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


/**
 *
 * @author Yogendra Rokaya
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AddOrderModel {
    private Connection connect() {
        // MySQL connection string
        String url = "jdbc:mysql://localhost:3306/resturantmanage";
        String user = "root";
        String password = "M@yogesh4165"; // Replace with your actual database password
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public boolean saveOrder(String customerName, String customerPhone, String[][] orderItems) {
        String insertCustomer = "INSERT INTO customers(customer_name, customer_phone) VALUES(?, ?)";
        String getCustomerId = "SELECT LAST_INSERT_ID() AS customer_id";
        String insertOrder = "INSERT INTO orders(customer_id, food_name, quantity, price) VALUES(?, ?, ?, ?)";

        try (Connection conn = this.connect();
             PreparedStatement pstmt1 = conn.prepareStatement(insertCustomer, PreparedStatement.RETURN_GENERATED_KEYS);
             PreparedStatement pstmt2 = conn.prepareStatement(insertOrder)) {

            // Insert customer
            pstmt1.setString(1, customerName);
            pstmt1.setString(2, customerPhone);
            pstmt1.executeUpdate();

            // Get the generated customer_id
            int customerId;
            try (ResultSet generatedKeys = pstmt1.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    customerId = generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Failed to retrieve customer ID.");
                }
            }

            // Insert order items
            for (String[] item : orderItems) {
                pstmt2.setInt(1, customerId); // Use retrieved customer_id
                pstmt2.setString(2, item[0]); // Food name
                pstmt2.setInt(3, Integer.parseInt(item[1])); // Quantity
                pstmt2.setDouble(4, Double.parseDouble(item[2])); // Price
                pstmt2.executeUpdate();
            }

            return true;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}


