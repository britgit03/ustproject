package com.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
	@Id
	@GeneratedValue
	private int customerId;
	private String customerName;
	private String password;
	private String confirmPassword;
	private String email;
	private String customerAddress;
	private String customerCity;
	private String customerState;
	private String pinCode;
	private int billAmount;
	
	
	
	
		

}
