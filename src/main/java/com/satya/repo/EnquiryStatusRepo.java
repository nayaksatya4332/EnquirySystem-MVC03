package com.satya.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.satya.entity.EnquiryStatus;

public interface EnquiryStatusRepo extends JpaRepository<EnquiryStatus, Integer> {

}
