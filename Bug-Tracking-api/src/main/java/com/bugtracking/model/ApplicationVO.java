package com.bugtracking.model;

import java.util.Date;
import java.util.Set;

import com.bugtracking.entity.Bug;

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
	private Set<Bug> bugs;	
}