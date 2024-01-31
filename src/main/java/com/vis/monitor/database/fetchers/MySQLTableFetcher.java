package com.vis.monitor.database.fetchers;

import org.springframework.stereotype.Service;

import com.vis.monitor.db.modal.Database;

import lombok.extern.log4j.Log4j2;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
@Log4j2
public class MySQLTableFetcher {

	public static List<String> fetchTableNames(Database database) {
	    List<String> tableNames = new ArrayList<>();

	    try {
	        Class.forName("com.mysql.cj.jdbc.Driver");
	        String url = database.getUrl();
	        try (Connection connection = DriverManager.getConnection(url, database.getUserName(), database.getPassword())) {
	        	log.info("Database URL: " + url);
	        	log.info("Database Username: " + database.getUserName());
	        	log.info("Database Password: " + database.getPassword());
	        	
 
	        	connection.setSchema(database.getInstance());
	        	log.info("Database Instance: " + database.getInstance());
	        	DatabaseMetaData metaData = connection.getMetaData();
	        	ResultSet tables = metaData.getTables(database.getInstance(), null, "%", new String[]{"TABLE"});


	            while (tables.next()) {
	                String tableName = tables.getString("TABLE_NAME");
	                log.info("the table names are " + tableName);
	                if (!isSystemTable(tableName)) {
	                    tableNames.add(tableName);
	                }
	            }
	        }
	    } catch (ClassNotFoundException | SQLException e) {
	        e.printStackTrace(); 
	    }

	    return tableNames;
	}

	private static boolean isSystemTable(String tableName) {
	    return tableName.startsWith("sys_") || tableName.startsWith("mysql_") || tableName.startsWith("information_schema");
	}
}