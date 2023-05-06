package com.reporting.model;



import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class ApplicationRequest {
	
	private int id;
	private String name;
	private Date createdOn;
	private String description;
	private String owner;
}