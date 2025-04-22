package com.satya.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.satya.entity.StdEnquiry;

public interface StdEnquiryRepo extends JpaRepository<StdEnquiry, Integer> {
	
}
