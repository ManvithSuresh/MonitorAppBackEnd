package com.vis.monitor.DatabaseTestConnection;

import java.util.List;

public interface TestConnectionService {
 TestConnection testDatabaseConnection(String url , String usename, String password,String databaseType);

List<TestConnection> getConnectionList();



TestConnection getConnectionListById(Long id);
 
}
