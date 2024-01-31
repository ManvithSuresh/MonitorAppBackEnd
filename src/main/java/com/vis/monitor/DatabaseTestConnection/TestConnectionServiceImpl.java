package com.vis.monitor.DatabaseTestConnection;

import java.sql.Connection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import lombok.extern.log4j.Log4j2;




@Service
@Log4j2
public class TestConnectionServiceImpl implements TestConnectionService {

	@Autowired
	private TestConnectionRepository tcRepo;
	
	@Override
	public TestConnection testDatabaseConnection(String url, String usename, String password, String databaseType) {
		// TODO Auto-generated method stub
		TestConnection testConnection=new TestConnection();
		testConnection.setUrl(url);
		testConnection.setPassword(password);
		testConnection.setDatabaseType(databaseType);
		testConnection.setUsername(usename);
		
		
		HikariDataSource dataSource=null;
		try {
			HikariConfig config=new HikariConfig();
			config.setJdbcUrl(url);
			config.setUsername(usename);
			config.setPassword(password);
		
			dataSource=new HikariDataSource(config);
			
			try(Connection connection=dataSource.getConnection()){
				testConnection.setConnectionResult(true);
			}
		}catch (Exception e) {
				// TODO: handle exception
		      log.error("Connection failed: " + e.getMessage());
	            testConnection.setConnectionResult(false);
			}finally {
				  if (dataSource != null) {
		                dataSource.close();
		}
	}

		 return tcRepo.save(testConnection);
	
	
	}

	@Override
	public List<TestConnection> getConnectionList() {
		// TODO Auto-generated method stub
		
		return tcRepo.findAll();
	}

	@Override
	public TestConnection getConnectionListById(Long id) {
		// TODO Auto-generated method stub
	return tcRepo.findById(id).orElse(null);
	}

}
	

