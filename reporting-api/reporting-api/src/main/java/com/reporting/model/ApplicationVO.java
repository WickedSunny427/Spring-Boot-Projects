package com.reporting.model;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApplicationVO {

	private int id;
	private String name;
	private Date createdOn;
	private String description;
	private String owner;
//	private Set<Bug> bugs;	
}