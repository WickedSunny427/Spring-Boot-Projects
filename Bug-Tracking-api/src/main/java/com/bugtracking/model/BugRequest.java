package com.bugtracking.model;



import java.util.Date;

import com.bugtracking.entity.Application;
import com.bugtracking.entity.Release;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BugRequest {

	private int id;
	private String type;
	private String applicationImpacted;
	private String assignedTo;
	private Date createdOn;
	private String createdBy;
	private String status;
	private String description;
	private Application application;
	private Release release;
}