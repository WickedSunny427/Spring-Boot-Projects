package com.bankingmanagement.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "LOAN_TBL")
public class Loan implements Serializable {

	private static final long serialVersionUID = 2225748739622807062L;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_gen")
	@SequenceGenerator(name = "customer_gen", sequenceName = "customer_id_sequence", allocationSize = 1)
	@Column(name = "LOAN_ID")
	int loanId;

	@Column(name = "LOAN_TYPE")
	String loanType;

	@Column(name = "LOAN_AMOUNT")
	double loanAmount;

	@ManyToOne
	@JoinColumn(name = "BRANCH_ID")
	private Branch branch;

	@ManyToOne
	@JoinColumn(name = "CUSTOMER_ID")
	Customer customer;
}