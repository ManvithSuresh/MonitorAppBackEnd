		package com.vis.monitor.QueryEditor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

        @Entity
		@Data
		@Table(name="query_editor")
		public class QueryEditor {
		
	     @Id
	     @GeneratedValue(strategy = GenerationType.IDENTITY)
	     private long id;
			
	     private String queryName;
	    
	     private String selectedDatabase;
	     
	     private String selectedTable;

	     @Column(columnDefinition = "TEXT")
	     private String query;
	     
	     
	     public QueryEditor(String queryName, String selectedDatabase, String selectedTable, String query) {
	         this.queryName = queryName;
	         this.selectedDatabase = selectedDatabase;
	         this.selectedTable = selectedTable;
	         this.query = query;
	     }
		}
