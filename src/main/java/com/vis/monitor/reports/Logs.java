package com.vis.monitor.reports;

import java.util.Date;

import lombok.Data;

@Data
public class Logs {

	    private Date timeStamp;
	    private String level;
	    private String message;
	    
	    public Logs(Date timestamp, String level, String message) {
	        this.timeStamp = timestamp;
	        this.level = level;
	        this.message = message;
	    }
}
