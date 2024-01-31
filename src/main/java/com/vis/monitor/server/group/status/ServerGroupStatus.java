package com.vis.monitor.server.group.status;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.vis.monitor.modal.ServerGroup;

import lombok.Data;

@Entity
@Data
public class ServerGroupStatus {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@OneToOne(cascade = {CascadeType.DETACH,CascadeType.PERSIST,CascadeType.REFRESH,CascadeType.REMOVE, CascadeType.ALL}, fetch = FetchType.EAGER)
    @JoinColumn(name = "serverGroupId", referencedColumnName = "id")
	private ServerGroup serverGroup;
	
	@JsonFormat(timezone = "IST")
	private Date downAt;
	
	@JsonFormat(timezone = "IST")
	private Date upAt;
	
	private Long duration;

}
