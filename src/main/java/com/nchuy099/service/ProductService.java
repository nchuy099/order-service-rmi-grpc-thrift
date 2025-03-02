package com.nchuy099.service;

import com.nchuy099.config.DatabaseConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductService {

    private final int PROCESSING_DELAY = 500;

    public double calculateTotal(String productId, int quantity) {
        try {
            Thread.sleep(PROCESSING_DELAY);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return getPrice(productId) * quantity;
    }

    public double getPrice(String productId) {
        String sql = "SELECT price FROM products WHERE product_id = ?";
        double price = -1;

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setString(1, productId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    price = resultSet.getDouble("price");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return price;
    }

}
