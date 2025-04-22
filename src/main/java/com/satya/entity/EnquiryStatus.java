package com.satya.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class EnquiryStatus {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer statusId;
	private String statusName;
}
