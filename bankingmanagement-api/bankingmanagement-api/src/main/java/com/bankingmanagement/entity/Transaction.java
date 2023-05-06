package com.bankingmanagement.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

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
@Table(name = "Transaction_TBL")
public class Transaction implements Serializable {

	private static final long serialVersionUID = -7835278963286465827L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "transaction_gen")
	@SequenceGenerator(name = "transaction_gen", sequenceName = "transaction_id_sequence", allocationSize = 1)
	@Column(name = "TRANSACTION_ID")
	private int transactionId;

	@Column(name = "TRANSACTION_TYPE")
	private String transactionType;

	@Column(name = "AMOUNT")
	private double amount;

	@Column(name = "TRANSACTION_DATE")
	private LocalDateTime transactionDate;

	@Column(name = "TRANSACTION_STATUS")
	private String status;

	@ManyToOne
	@JoinColumn(name = "ACCOUNT_NUMBER")
	private Account account;

	@ManyToOne
	@JoinColumn(name = "CUST_ID")
	private Customer customer;

}
