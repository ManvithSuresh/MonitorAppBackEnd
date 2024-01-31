package com.vis.monitor.db.modal;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.vis.monitor.modal.ServerHourlyStatus;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DatabaseHourlyStatus {
	@JsonFormat(pattern = "yyyy-MM-dd HH:00:00" , timezone = "IST")
	private Date startTime;
	@JsonFormat(pattern = "yyyy-MM-dd HH:59:59" , timezone = "IST")
	private Date endTime;
	private Integer hour;
	private Long duration;
	
}
