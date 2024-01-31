package com.vis.monitor.modal;


import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.FetchType;

import lombok.Data;

@Embeddable

@Data
public class GroupingPorts { 

	

    private Integer port;
    private Boolean isActive = false;
//
//	     @ElementCollection(fetch = FetchType.EAGER)
//	     private List<Integer> ports;
//	   
////	    private Boolean isActive = false;

}
		
