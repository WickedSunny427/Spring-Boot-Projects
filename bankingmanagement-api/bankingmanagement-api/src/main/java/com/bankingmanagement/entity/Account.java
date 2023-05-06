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
@Table(name = "ACCOUNT_TBL")
public class Account implements Serializable {

	private static final long serialVersionUID = -7835278963286465827L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "account_gen")
	@SequenceGenerator(name = "account_gen", sequenceName = "account_id_sequence", allocationSize = 1)
	@Column(name = "ACCOUNT_NUMBER")
	int accountNumber;

	@Column(name = "ACCOUNT_TYPE")
	String accountType;

	@Column(name = "ACCOUNT_BALANCE")
	double accountBalance;

	@ManyToOne
	@JoinColumn(name = "BRANCH_ID")
	private Branch branch;

	@ManyToOne
	@JoinColumn(name = "CUSTOMER_ID")
	Customer customer;

}