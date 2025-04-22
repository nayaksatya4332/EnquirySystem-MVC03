package com.satya.service;

import java.util.List;

import com.satya.binding.DashboardData;
import com.satya.binding.EnquiryCriteria;
import com.satya.binding.EnquiryForm;
import com.satya.entity.StdEnquiry;
import com.satya.util.Account;

public interface StdEnquiryService {
	public List<String> getCourses();

	public List<String> getEnqStatuses();
	
	public DashboardData getPerfomance(Integer usrId);
	
	public Account addEnq(EnquiryForm enq);
	
	public Account updateEnq(Integer enqId,EnquiryForm enqForm);
	
	public Account delete(Integer id);
	
	public StdEnquiry getEnq(int enqId);
	
	public List<StdEnquiry> getEnq();

	public List<StdEnquiry> getEnq(Integer usrId, EnquiryCriteria criteria);

}
