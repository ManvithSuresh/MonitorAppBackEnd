package com.vis.monitor.DatabaseTestConnection;

import java.util.List;

import org.apache.http.conn.ConnectionRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TestConnectionController {
	
	@Autowired
	private TestConnectionService tsService;
	

@PostMapping("/test-connection")
private TestConnection testDatabaseConnection(@RequestBody TestConnection testConnection) {
	String url=testConnection.getUrl();
	String password=testConnection.getPassword();
	String username=testConnection.getUsername();
	String type=testConnection.getDatabaseType();
	
	return tsService.testDatabaseConnection(url,username,password,type);
		
}


@GetMapping("/result")
public ResponseEntity<List<TestConnection> >getConnectionList(){
	List<TestConnection> result=tsService.getConnectionList();
	return ResponseEntity.ok(result);
}


@GetMapping("/results/{id}")
public ResponseEntity<TestConnection>getConnectionById(@PathVariable Long id){
TestConnection testConnection=tsService.getConnectionListById(id);
if(testConnection !=null) {
	return ResponseEntity.ok(testConnection);
}else {
	return ResponseEntity.notFound().build();
}

}
}