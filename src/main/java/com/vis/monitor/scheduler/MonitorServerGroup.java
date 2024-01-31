			package com.vis.monitor.scheduler;
			
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
			
			
			import com.vis.monitor.modal.ServerGroup;
			import com.vis.monitor.modal.User;
			
			import lombok.Data;
			
			
			
			
			@Entity
			@Data
			@Table(name="monitor_request_server_group")
			public class MonitorServerGroup {
			
				@Id
				@GeneratedValue(strategy = GenerationType.IDENTITY)
				private Long id;
				
			     @OneToOne(cascade = {CascadeType.DETACH, CascadeType.REFRESH, CascadeType.REMOVE}, fetch = FetchType.LAZY)
				@JoinColumn(name = "serverGroupId", referencedColumnName = "id")
				private ServerGroup serverGroup;
				
			     @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.REFRESH, CascadeType.REMOVE, CascadeType.PERSIST}, fetch = FetchType.LAZY)
			     @JoinTable(name = "monitor_request_server_group_user", 
				               joinColumns = @JoinColumn(name = "monitor_request_server_group_id"), 
				               inverseJoinColumns = @JoinColumn(name = "user_id"))
					private List<User> users;
		
		
			}
			
			
			
			
