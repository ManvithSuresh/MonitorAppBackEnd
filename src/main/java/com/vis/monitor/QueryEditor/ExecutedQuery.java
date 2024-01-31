//package com.vis.monitor.QueryEditor;
//
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.JoinColumn;
//
//import javax.persistence.ManyToOne;
//import javax.persistence.Table;
//
//import lombok.Data;
//
//@Entity
//@Data
//@Table(name="executed_query")
//public class ExecutedQuery {
//
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	private Long id;
//	
//	
//	private String sqlQuery;
//	
//	@ManyToOne
//    @JoinColumn(name = "query_editor_id", nullable = false)
//	private QueryEditor queryEditor;
//}
