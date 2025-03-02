package com.nchuy099.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConfig {
    private static final HikariDataSource dataSource;

    static {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:mysql://localhost:3306/myDb");
        config.setUsername("root");
        config.setPassword("12345678");
        config.setMaximumPoolSize(10); // Giới hạn tối đa 10 kết nối
        config.setMinimumIdle(2); // Luôn giữ ít nhất 2 kết nối sẵn sàng
        config.setIdleTimeout(30000); // Đóng kết nối nếu không dùng sau 30s
        config.setMaxLifetime(1800000); // Đóng kết nối cũ sau 30 phút
        config.setConnectionTimeout(3000); // Timeout nếu không lấy được kết nối trong 3s

        dataSource = new HikariDataSource(config);
    }

    public static DataSource getDataSource() {
        return dataSource;
    }
}
