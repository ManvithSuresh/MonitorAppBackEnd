package com.vis.monitor.modal;

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
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.vis.monitor.db.modal.Database;
import com.vis.monitor.ws.modal.WebService;

import lombok.Data;


@Data
@Entity
@Table(name = "monitor_requests")
public class MonitorRequest {
   @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "mrIdSeq")
	private Long id;

	private String name; 

	@OneToOne(cascade = {CascadeType.DETACH,	CascadeType.REFRESH,CascadeType.REMOVE,}, fetch = FetchType.EAGER)
    @JoinColumn(name = "serverId", referencedColumnName = "id")
	private Server server;
	
	@OneToOne(cascade = {CascadeType.DETACH,	CascadeType.REFRESH,CascadeType.REMOVE,}, fetch = FetchType.EAGER)
    @JoinColumn(name = "servicesId", referencedColumnName = "id")
	private ServerGroup serverServices;
//	@ManyToMany(cascade = {CascadeType.DETACH,CascadeType.REFRESH,CascadeType.REMOVE,CascadeType.PERSIST}, fetch = FetchType.LAZY)
//    @JoinColumn(name = "userIds", referencedColumnName = "id")
	
//	  @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.REFRESH, CascadeType.REMOVE, CascadeType.PERSIST}, fetch = FetchType.LAZY)
//    @JoinTable(name = "monitor_request_user", 
//               joinColumns = @JoinColumn(name = "monitor_request_id"), 
//               inverseJoinColumns = @JoinColumn(name = "user_id"))
//	private List<User> users;
	@ManyToMany(cascade = { CascadeType.DETACH, CascadeType.REFRESH, CascadeType.REMOVE, CascadeType.PERSIST }, fetch = FetchType.LAZY)
	@JoinTable(name = "monitor_request_user", joinColumns = @JoinColumn(name = "monitor_request_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
	private List<User> users;
}
