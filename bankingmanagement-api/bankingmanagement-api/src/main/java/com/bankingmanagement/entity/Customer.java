package com.bankingmanagement.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "Customer_TBL")
public class Customer implements Serializable {

	private static final long serialVersionUID = 2662284133660580061L;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_gen")
	@SequenceGenerator(name = "customer_gen", sequenceName = "customer_id_sequence", allocationSize = 1)
	@Column(name = "CUSTOMER_ID")
	int custId;

	@Column(name = "CUSTOMER_NAME")
	String custName;

	@Column(name = "CUSTOMER_PHONE")
	long custPhone;

	@Column(name = "CUSTOMER_ADDRESS")
	String custAddress;

	@OneToMany(mappedBy = "customer",fetch = FetchType.EAGER)
	Set<Loan> loans;
	
	@OneToMany(mappedBy = "customer",fetch = FetchType.EAGER)
	Set<Account> accounts;
}