package com.vis.monitor.database.memory;

import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Service
public class DatabaseDashboardServiceImpl implements DatabaseDashboardService {

    @Override
    public List<DatabaseDashboardStatus> getServerStatus() {
        String url = "jdbc:mysql://localhost:3306/monitor_app";
        String password = "root";
        String userName = "root";

        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(url);
        config.setUsername(userName);
        config.setPassword(password);

        List<DatabaseDashboardStatus> statusList = new ArrayList<>();

        try (HikariDataSource dataSource = new HikariDataSource(config);
             Connection connection = dataSource.getConnection()) {

            statusList = fetchServerStatus(connection);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return statusList;
    }

    private List<DatabaseDashboardStatus> fetchServerStatus(Connection connection) throws SQLException {
        List<DatabaseDashboardStatus> statusList = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement("SHOW GLOBAL STATUS")) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    DatabaseDashboardStatus status = new DatabaseDashboardStatus();
                    status.setVariableName(resultSet.getString("Variable_name"));
                    status.setValue(resultSet.getString("Value"));
                    // Set other fields as needed
                    statusList.add(status);
                }
            }
        }

        return statusList;
    }
}
