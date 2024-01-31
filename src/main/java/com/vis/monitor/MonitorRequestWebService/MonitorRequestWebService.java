package com.vis.monitor.MonitorRequestWebService;

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

import com.vis.monitor.modal.User;
import com.vis.monitor.ws.modal.WebService;

import lombok.Data;

@Entity
@Data
@Table(name ="monitor_requests_webservice")
public class MonitorRequestWebService {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToMany(cascade = {CascadeType.DETACH, CascadeType.REFRESH, CascadeType.REMOVE, CascadeType.PERSIST}, fetch = FetchType.LAZY)
    @JoinTable(name = "monitor_request_webservice_user", 
               joinColumns = @JoinColumn(name = "monitor_request_database_id"), 
               inverseJoinColumns = @JoinColumn(name = "user_id"))
	private List<User> users;
	
	@OneToOne(cascade = {CascadeType.DETACH,CascadeType.REFRESH,CascadeType.REMOVE,}, fetch = FetchType.EAGER)
    @JoinColumn(name = "WebserviceIds", referencedColumnName = "id")
	private WebService webservice;
	
	

}
