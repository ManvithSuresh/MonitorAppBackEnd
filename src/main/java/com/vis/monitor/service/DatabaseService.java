package com.vis.monitor.service;

import java.util.List;


import com.vis.monitor.db.modal.Database;



public interface DatabaseService {
	public Database addDatabase(Database server);

	public Database updateDatabase(Database server);

	public List<Database> getDatabase();

	public Database getDatabase(Long id);

	public Database deleteDatabase(Long id);


	

	public List<String> getTables(String databaseName);

	 
}
