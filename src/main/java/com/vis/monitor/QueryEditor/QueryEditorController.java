package com.vis.monitor.QueryEditor;


import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.vis.monitor.db.modal.Database;
import com.vis.monitor.repository.DatabaseRepository;
import com.vis.monitor.service.DatabaseService;

import org.springframework.util.ObjectUtils;




@RestController
@RequestMapping("api")
public class QueryEditorController {

	@Autowired
	private QueryEditorService qeService;
	
	@Autowired
	private DatabaseService dbService;
	
	
	@Autowired
	private QueryEditorRepository qeRepo;

    @Autowired
    private DatabaseRepository databaseRepository;
    
    
   
   
    @GetMapping("/databases")
    public ResponseEntity<List<Database>> getDatabases() {
        List<Database> databases = dbService.getDatabase();
        return ResponseEntity.ok(databases);
    }
	
    @PostMapping("/saveQueryEditorDetails")
    public ResponseEntity<String> saveQueryEditor(@ModelAttribute  QueryEditor queryEditor) {
        try {
            // Validate if the selected database and table are not empty
        	if (ObjectUtils.isEmpty(queryEditor.getSelectedDatabase()) || ObjectUtils.isEmpty(queryEditor.getSelectedTable())) {
        	    return ResponseEntity.badRequest().body("Selected database and table are required.");
        	}

            // Save the QueryEditor entity
            QueryEditor savedQueryEditor = qeService.saveQueryEditor(queryEditor);

            return ResponseEntity.ok("QueryEditor saved successfully with ID: " + savedQueryEditor.getId());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving QueryEditor.");
        }
    }
    
	 @PostMapping("/execute")
	    public ResponseEntity<String> executeQuery(@RequestBody QueryEditor queryEditor) {
	        String databaseName = queryEditor.getSelectedDatabase();
	        String tableName = queryEditor.getSelectedTable();

	        // Get tables for the selected database
	        List<String> tables = dbService.getTables(databaseName);

	        // Validate if the selected table is valid
	        if (tables.contains(tableName)) {
	            // Execute the query (you can use queryName or any other field in QueryEditor)
	            String result = queryEditor.getQueryName() + " executed successfully!";
	            return ResponseEntity.ok(result);
	        } else {
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid table selected.");
	        }
	    }
	
	 
}
		
		 
	
