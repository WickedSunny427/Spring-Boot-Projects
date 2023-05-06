package com.bankingmanagement.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "BANK_TBL")
public class Bank implements Serializable {

	private static final long serialVersionUID = -5547304074475429760L;
	@Id
	@Column(name = "BANK_CODE")
	int bankCode;

	@Column(name = "BANK_NAME")
	String bankName;

	@Column(name = "BANK_ADDRESS")
	String bankAddress;

	@OneToMany(mappedBy = "bank")
	Set<Branch> branches;

}