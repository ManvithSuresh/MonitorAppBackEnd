package com.vis.monitor.modal;


import javax.persistence.*;

import org.hibernate.annotations.CreationTimestamp;

import com.vis.monitor.db.modal.Database;

import com.vis.monitor.ws.modal.WebService;

import lombok.Data;

import java.util.Date;

@Entity
@Data
@Table(name = "monitor_statuses")
public class MonitorStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "msIdSeq")
    private Long id;

    @OneToOne(cascade = {CascadeType.DETACH,CascadeType.PERSIST,CascadeType.REFRESH,CascadeType.REMOVE}, fetch = FetchType.EAGER)
    @JoinColumn(name = "userId", referencedColumnName = "id")
    private User user;
    
	@OneToOne(cascade = {CascadeType.DETACH,CascadeType.PERSIST,CascadeType.REFRESH,CascadeType.REMOVE}, fetch = FetchType.EAGER)
    @JoinColumn(name = "serverId", referencedColumnName = "id")
	private Server server;
	



    private Boolean isSent; 

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date sentAt;
  
}
