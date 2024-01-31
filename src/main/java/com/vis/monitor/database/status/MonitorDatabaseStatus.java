package com.vis.monitor.database.status;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;


import com.vis.monitor.db.modal.Database;
import com.vis.monitor.modal.User;
import com.vis.monitor.ws.modal.WebService;

import lombok.Data;

@Entity
@Data
@Table(name = "monitor_database_statuses")
public class MonitorDatabaseStatus {

	
	     @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "msIdSeq")
	    private Long id;

	    @OneToOne(cascade = {CascadeType.DETACH,CascadeType.PERSIST,CascadeType.REFRESH,CascadeType.REMOVE}, fetch = FetchType.EAGER)
	    @JoinColumn(name = "userId", referencedColumnName = "id")
	    private User user;
	
	
	@OneToOne(cascade = {CascadeType.DETACH,CascadeType.PERSIST,CascadeType.REFRESH,CascadeType.REMOVE}, fetch = FetchType.EAGER)
    @JoinColumn(name = "databaseId", referencedColumnName = "id")
	private Database database;
	
	   private Boolean isSent; 

	    @CreationTimestamp
	    @Temporal(TemporalType.TIMESTAMP)
	    private Date sentAt;

		
}
