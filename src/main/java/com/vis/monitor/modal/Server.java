		package com.vis.monitor.modal;
		
	import java.util.List;
	
	import javax.persistence.CascadeType;
	import javax.persistence.CollectionTable;
	import javax.persistence.Column;
	import javax.persistence.ElementCollection;
	import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
		import javax.persistence.GenerationType;
		import javax.persistence.Id;
		import javax.persistence.JoinColumn;
	import javax.persistence.JoinTable;
	import javax.persistence.ManyToMany;
	import javax.persistence.OneToMany;
	
	import lombok.Data;
		
	@Entity
	@Data	public class Server {
		
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Long id;
		
		private String name;
		
		private String host;
	
		  @ElementCollection(fetch = FetchType.EAGER)
		    @JoinTable(name = "port_group")
		    @JoinColumn(name = "server_id")
	      List<Ports> ports;
		  
		 
		 
		private Boolean isActive = false;
	
	
		}
