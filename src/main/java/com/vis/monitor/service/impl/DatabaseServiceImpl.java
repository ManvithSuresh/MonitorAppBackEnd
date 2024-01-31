package com.vis.monitor.service.impl;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.sql.DataSource;

import com.vis.monitor.QueryEditor.DynamicDataSource;

import com.vis.monitor.db.modal.Database;
	import com.vis.monitor.repository.DatabaseRepository;
import com.vis.monitor.service.DatabaseService;

import lombok.extern.log4j.Log4j2;

import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.stereotype.Service;

	@Service
	@Log4j2
	public class DatabaseServiceImpl implements DatabaseService {

//	    private final DatabaseRepository databaseRepository;
//
//	    @Autowired
//	    public DatabaseServiceImpl(DatabaseRepository databaseRepository) {
//	        this.databaseRepository = databaseRepository;
//	    }
		
		@Autowired
		private DatabaseRepository databaseRepository;

	    @Override
	    public Database addDatabase(Database database) {
	        return databaseRepository.save(database);
	    }

	    @Override
	    public Database updateDatabase(Database database) {
	       
	        return databaseRepository.save(database);
	    }

	    @Override
	    public List<Database> getDatabase() {
	        
	        return databaseRepository.findAll();
	    }

	    @Override
	    public Database getDatabase(Long id) {
	        
	        return databaseRepository.findById(id).orElse(null);
	    }
	    
	    

	    @Override
	    public Database deleteDatabase(Long id) {
	       Database database = getDatabase(id);
	        if (database != null) {
	            databaseRepository.delete(database);
	        }
	        return database;
	    }
	    
	    
	    
//	    for query editor
	    @Override
	    public List<String> getTables(String databaseName) {
	        Database database = databaseRepository.findByName(databaseName);
	        log.info("the database is"+databaseName);
	        if (database != null) {
	            return Arrays.asList(database.getTables().split(","));
	        }
	        return Collections.emptyList();
	    }
	  
	    
	    
	    
	    
	}