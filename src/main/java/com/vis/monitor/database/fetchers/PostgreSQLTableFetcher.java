package com.vis.monitor.database.fetchers;

import org.springframework.stereotype.Service;

import com.vis.monitor.db.modal.Database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class PostgreSQLTableFetcher {

    public List<String> fetchTableNames(Database database) {
        List<String> tableNames = new ArrayList<>();

        try {
            Class.forName("org.postgresql.Driver");
            String url = database.getUrl();
            try (Connection connection = DriverManager.getConnection(url, database.getUserName(), database.getPassword())) {
                DatabaseMetaData metaData = connection.getMetaData();
                ResultSet tables = metaData.getTables(null, null, "%", null);

                while (tables.next()) {
                    tableNames.add(tables.getString("TABLE_NAME"));
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace(); // Handle the exception based on your application's needs
        }

        return tableNames;
    }
}
