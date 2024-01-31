	package com.vis.monitor.modal;
	
	import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import javax.persistence.GeneratedValue;
	import javax.persistence.GenerationType;
	import javax.persistence.Id;
	import javax.persistence.JoinTable;
	import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.JoinColumn;
	import lombok.Data;
	
	@Entity
	@Data
	public class ServerGroup {
	

	
	    
		    @Id
		    @GeneratedValue(strategy = GenerationType.IDENTITY)
		    private Long id;

		

	
	    private String groupName;
	
	  
	//     @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	//     @JoinColumn(name = "server_group_id")
	//     @Fetch(FetchMode.SUBSELECT)
	//     private List<GroupingPorts> services;
	// }
//	    
	    @ElementCollection
	    @JoinTable(name = "server_group_services")
	    @JoinColumn(name = "server_group_id")
	    @Fetch(FetchMode.SUBSELECT)
	   private List<GroupingPorts> services;
	}
//	    @Embedded
//	    @AttributeOverrides({
//	            @AttributeOverride(name = "isActive", column = @Column(name = "services_isActive"))
//	    })
//	    private GroupingPorts services ;
	
