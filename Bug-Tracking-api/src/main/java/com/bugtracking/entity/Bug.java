package com.bugtracking.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;

@Data
@Entity
@Table(name = "BUG_TBL")
public class Bug implements Serializable {

	private static final long serialVersionUID = 3382528179548773084L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bug_gen")
	@SequenceGenerator(name = "bug_gen", sequenceName = "BUGTRACKING_BUG_SEQUENCE", allocationSize = 1)
	@Column(name = "BUG_ID")
	private int id;

	private String type;

	@Column(name = "APPLICATION_IMPACTED")
	private String applicationImpacted;

	@Column(name = "ASSIGNED_TO")
	private String assignedTo;

	@Temporal(TemporalType.DATE)
	@Column(name = "CREATED_ON")
	private Date createdOn;

	@Column(name = "CREATED_BY")
	private String createdBy;

	private String status;
	private String description;

	@ManyToOne
	@JoinColumn(name = "APPLICATION_ID")
	private Application application;

	@ManyToOne
	@JoinColumn(name = "RELEASE_ID")
	private Release release; 	
}