package com.satya.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.satya.binding.DashboardData;
import com.satya.binding.EnquiryCriteria;
import com.satya.binding.EnquiryForm;
import com.satya.entity.StdEnquiry;
import com.satya.entity.UserDetails;
import com.satya.repo.CourseRepo;
import com.satya.repo.EnquiryStatusRepo;
import com.satya.repo.StdEnquiryRepo;
import com.satya.repo.UserDetailsRepo;
import com.satya.util.Account;

@Service
public class StdEnquiryServiceImpl implements StdEnquiryService {
	@Autowired
	private UserDetailsRepo userRepo;
	@Autowired
	private CourseRepo courseRepo;
	@Autowired
	private EnquiryStatusRepo enqRepo;
	@Autowired
	private StdEnquiryRepo stdRepo;
	@Autowired
	private HttpSession session;

	@Override
	public List<String> getCourses() {
		List<String> courses = this.courseRepo.findAll().stream().map(c -> c.getCourseName()).toList();
		return courses;
	}

	@Override
	public List<String> getEnqStatuses() {
		List<String> enquiries = this.enqRepo.findAll().stream().map(e -> e.getStatusName()).toList();
		return enquiries;
	}

	@Override
	public DashboardData getPerfomance(Integer id) {
		DashboardData data = new DashboardData();

		Optional<UserDetails> userEntity = this.userRepo.findByUserId(id);
		if (userEntity.isPresent()) {
			UserDetails user = userEntity.get();
			int totalEnquiries = user.getEnquiries().size();
			int totalEnrolled = user.getEnquiries().stream().filter(e -> e.getEnquiryStatus().equals("Enrolled"))
					.collect(Collectors.toList()).size();
			int totalLost = user.getEnquiries().stream().filter(e -> e.getEnquiryStatus().equals("Lost"))
					.collect(Collectors.toList()).size();

			data.setEnrolled(totalEnrolled);
			data.setTotalEnq(totalEnquiries);
			data.setLost(totalLost);
		}
		return data;
	}

	@Override
	public Account addEnq(EnquiryForm enq) {
		StdEnquiry stdEnq = new StdEnquiry();
		if (enq.getClassMode().isEmpty() || enq.getCourseName().isEmpty() || enq.getEnquiryStatus().isEmpty()
				|| enq.getPhNo().isEmpty() || enq.getStudentName().isEmpty())
			return Account.NOT_ADDED;
		BeanUtils.copyProperties(enq, stdEnq);
		UserDetails user = this.userRepo.findByUserId((int) this.session.getAttribute("userId")).get();
		if (user == null)
			return Account.NOT_LOGGEDIN;
		stdEnq.setUser(user);
		this.stdRepo.save(stdEnq);

		return Account.ADDED;
	}

	@Override
	public Account updateEnq(Integer enqId, EnquiryForm enqForm) {
		Optional<StdEnquiry> enq = this.stdRepo.findById(enqId);
		if (enq.isPresent()) {
			StdEnquiry enquiry = enq.get();
			enquiry.setStudentName(enqForm.getStudentName());
			enquiry.setPhNo(enqForm.getPhNo());
			enquiry.setClassMode(enqForm.getClassMode());
			enquiry.setCourseName(enqForm.getCourseName());
			enquiry.setEnquiryStatus(enqForm.getEnquiryStatus());
			
			this.stdRepo.save(enquiry);
			return Account.ENQ_UPDATED;
		}
		return Account.UPDATE_FAILED;
	}

	@Override
	public Account delete(Integer id) {
		this.stdRepo.deleteById(id);
		return Account.ENQ_DELETED;
	}

	@Override
	public List<StdEnquiry> getEnq() {
		Integer id = (Integer) this.session.getAttribute("userId");
		Optional<UserDetails> userEntity = this.userRepo.findByUserId(id);
		if (userEntity.isPresent()) {
			UserDetails user = userEntity.get();
			List<StdEnquiry> enqs = user.getEnquiries();
			return enqs;
		}
		return null;
	}

	@Override
	public List<StdEnquiry> getEnq(Integer usrId, EnquiryCriteria criteria) {
		Optional<UserDetails> userEntity = this.userRepo.findByUserId(usrId);
		UserDetails user = userEntity.get();
		List<StdEnquiry> enqList = user.getEnquiries();

		if (!criteria.getCourseName().isEmpty()) {
			enqList = enqList.stream().filter(enq -> enq.getCourseName().equals(criteria.getCourseName()))
					.collect(Collectors.toList());
		}
		if (!criteria.getClassMode().isEmpty()) {
			enqList = enqList.stream().filter(enq -> enq.getClassMode().equals(criteria.getClassMode()))
					.collect(Collectors.toList());
		}
		if (!criteria.getEnquiryStatus().isEmpty()) {
			enqList = enqList.stream().filter(enq -> enq.getEnquiryStatus().equals(criteria.getEnquiryStatus()))
					.collect(Collectors.toList());
		}

		return enqList;
	}

	@Override
	public StdEnquiry getEnq(int enqId) {
		Optional<StdEnquiry> enq = this.stdRepo.findById(enqId);
		return enq.get();
	}

}
