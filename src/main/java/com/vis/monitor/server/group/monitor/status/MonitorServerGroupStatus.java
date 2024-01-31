package com.vis.monitor.server.group.monitor.status;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;

import com.vis.monitor.modal.ServerGroup;
import com.vis.monitor.modal.User;

import lombok.Data;

@Entity
@Data
public class MonitorServerGroupStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = {CascadeType.DETACH,CascadeType.PERSIST,CascadeType.REFRESH,CascadeType.REMOVE}, fetch = FetchType.EAGER)
    @JoinColumn(name = "userId", referencedColumnName = "id")
    private User user;
    
	@OneToOne(cascade = {CascadeType.DETACH,CascadeType.PERSIST,CascadeType.REFRESH,CascadeType.REMOVE}, fetch = FetchType.EAGER)
    @JoinColumn(name = "serverGroupId", referencedColumnName = "id")
	private ServerGroup serverGroup;
	

          private Boolean isSent; 

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date sentAt;
}
