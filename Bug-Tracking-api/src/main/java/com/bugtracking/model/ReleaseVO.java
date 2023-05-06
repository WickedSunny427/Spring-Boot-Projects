package com.bugtracking.model;

import java.util.Date;
import java.util.Set;

import com.bugtracking.entity.Bug;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReleaseVO {
	private int id;
	private String name;
	private Date releaseDate;
	private String status;
	private String comments;
	private Set<Bug> bugs;
}