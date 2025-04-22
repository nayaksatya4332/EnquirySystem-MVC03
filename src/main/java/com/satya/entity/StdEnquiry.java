package com.satya.entity;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;
import lombok.ToString;

@Entity
@Data
public class StdEnquiry {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer enquiryId;
	private String studentName;
	private String phNo;
	private String classMode;
	private String courseName;
	private String enquiryStatus;
	@CreationTimestamp
	private LocalDate createdDate;
	@UpdateTimestamp
	private LocalDate updatedDate;
	@ManyToOne
	@ToString.Exclude
	@JoinColumn(name="User_Entity")
	private UserDetails user;
}
