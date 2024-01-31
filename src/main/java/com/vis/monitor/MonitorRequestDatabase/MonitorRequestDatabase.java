package com.vis.monitor.MonitorRequestDatabase;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.vis.monitor.db.modal.Database;
import com.vis.monitor.modal.User;

import lombok.Data;

@Data
@Entity
@Table(name="monitor_request_database")
public class MonitorRequestDatabase {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
//	@ManyToMany(cascade = {CascadeType.DETACH,CascadeType.REFRESH,CascadeType.REMOVE,CascadeType.PERSIST}, fetch = FetchType.LAZY)
//    @JoinColumn(name = "userIds", referencedColumnName = "id")
//	
	@ManyToMany(cascade = {CascadeType.DETACH, CascadeType.REFRESH, CascadeType.REMOVE, CascadeType.PERSIST}, fetch = FetchType.LAZY)
    @JoinTable(name = "monitor_request_database_user", 
               joinColumns = @JoinColumn(name = "monitor_request_database_id"), 
               inverseJoinColumns = @JoinColumn(name = "user_id"))
	private List<User> users;
	
	@OneToOne(cascade = {CascadeType.DETACH,CascadeType.REFRESH,CascadeType.REMOVE,}, fetch = FetchType.EAGER)
    @JoinColumn(name = "databaseIdS", referencedColumnName = "id")
	private Database database;
}
