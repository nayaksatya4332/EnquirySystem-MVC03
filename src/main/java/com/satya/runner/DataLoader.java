package com.satya.runner;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.satya.entity.Course;
import com.satya.entity.EnquiryStatus;
import com.satya.repo.CourseRepo;
import com.satya.repo.EnquiryStatusRepo;

@Component
public class DataLoader implements ApplicationRunner {
	@Autowired
	private CourseRepo courseRepo;
	@Autowired
	private EnquiryStatusRepo enqStatus;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		this.courseRepo.deleteAll();
		this.enqStatus.deleteAll();
		
		Course c1 = new Course();
		c1.setCourseName("JavaFullStack");
		Course c2 = new Course();
		c2.setCourseName("DevOps");
		Course c3 = new Course();
		c3.setCourseName("AWS");

		EnquiryStatus s1 = new EnquiryStatus();
		s1.setStatusName("New");
		EnquiryStatus s2 = new EnquiryStatus();
		s2.setStatusName("Enrolled");
		EnquiryStatus s3 = new EnquiryStatus();
		s3.setStatusName("Lost");

		List<Course> courseList = Arrays.asList(c1, c2, c3);
		List<EnquiryStatus> statusList = Arrays.asList(s1, s2, s3);

		this.courseRepo.saveAll(courseList);
		this.enqStatus.saveAll(statusList);

	}

}
