package com.vis.monitor.QueryEditor;

import javax.sql.DataSource;

import com.vis.monitor.db.modal.Database;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import lombok.extern.log4j.Log4j2;
@Log4j2
public class DynamicDataSource{
	
public static DataSource createDataSource(Database database) {
	try {
	HikariConfig config=new HikariConfig();
	
    String type = database.getType();
    log.info("the type is"+type);
    String url = database.getUrl();
    log.info("the url is"+url);
    String username = database.getUserName();
    log.info("the username is"+username);
    String password = database.getPassword();
    log.info("the password is"+password);
    

	
	
	  if ("mysql".equalsIgnoreCase(type)) {
	        config.setDriverClassName("com.mysql.cj.jdbc.Driver");
	      
	    } else if ("postgresql".equalsIgnoreCase(type)) {
	        config.setDriverClassName("org.postgresql.Driver");
	       
	    } else if ("mssql".equalsIgnoreCase(type)) {
	        config.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
	       
	    } else if ("mongodb".equalsIgnoreCase(type)) {
	        config.setDriverClassName("mongodb.jdbc.MongoDriver");
	        
	    } else if ("mariadb".equalsIgnoreCase(type)) {
	        config.setDriverClassName("org.mariadb.jdbc.Driver");
	      
	    }
		config.setMaximumPoolSize(10);
		config.setConnectionTimeout(30000);
		config.setIdleTimeout(600000);
		 config.setJdbcUrl(url);
		 config.setUsername(username); 
         config.setPassword(password); 
	return new  HikariDataSource(config);

} catch (Exception e) {
  
    throw new RuntimeException("Failed to create DataSource", e);
}

}}
