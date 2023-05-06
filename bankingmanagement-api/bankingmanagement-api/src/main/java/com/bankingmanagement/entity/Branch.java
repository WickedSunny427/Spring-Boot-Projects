package com.bankingmanagement.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "BRANCH_TBL")
public class Branch implements Serializable {

	private static final long serialVersionUID = 1237742627449093714L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "branch_gen")
	@SequenceGenerator(name = "branch_gen", sequenceName = "branch_id_sequence", allocationSize = 1)
	@Column(name = "BRANCH_ID")
	int branchId;

	@Column(name = "BRANCH_NAME")
	String branchName;

	@Column(name = "BRANCH_ADDRESS")
	String branchAddress;

	@ManyToOne
	@JoinColumn(name = "BANK_CODE")
	Bank bank;

	@OneToMany(mappedBy = "branch")
	Set<Account> accounts;
	
	@OneToMany(mappedBy = "branch")
	Set<Loan> loans;
	
}