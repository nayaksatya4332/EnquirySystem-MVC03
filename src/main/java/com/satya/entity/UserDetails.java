package com.satya.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Data;

@Entity
@Data
public class UserDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer userId;
	private String userName;
	private String email;
	private String phNo;
	private String pwd;
	private String accountStatus;
	@OneToMany(cascade = CascadeType.ALL,mappedBy = "user")
	private List<StdEnquiry> enquiries;
}
