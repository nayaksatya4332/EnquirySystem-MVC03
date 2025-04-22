package com.satya.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.satya.binding.DashboardData;
import com.satya.binding.EnquiryCriteria;
import com.satya.binding.EnquiryForm;
import com.satya.entity.StdEnquiry;
import com.satya.repo.StdEnquiryRepo;
import com.satya.service.StdEnquiryService;
import com.satya.util.Account;

@Controller
public class EnquiryController {
	@Autowired
	private HttpSession session;
	@Autowired
	private StdEnquiryService stdService;
	@Autowired
	private StdEnquiryRepo stdRepo;

	@GetMapping("/dashboard")
	public String dashboard(Model model) {
		Integer userId = (Integer) this.session.getAttribute("userId");
		DashboardData dashboardData = this.stdService.getPerfomance(userId);
		model.addAttribute("data", dashboardData);
		return "dashboard";
	}

	@GetMapping("/enquiry")
	public String enquiry(Model model) {
		this.viewInitialise(model);
		EnquiryForm enqForm = new EnquiryForm();
		if (session.getAttribute("enq") != null) {
			StdEnquiry enq = (StdEnquiry) session.getAttribute("enq");
			BeanUtils.copyProperties(enq, enqForm);
			session.removeAttribute("enq");
		}
		model.addAttribute("enqForm", enqForm);
		return "addenquiry";
	}

	@PostMapping("/enquiry")
	public String addEnquiry(@ModelAttribute("enqForm") EnquiryForm enqData, Model model) {
		Account status = null;
		if (session.getAttribute("enqId") != null) {
			status = this.stdService.updateEnq((int) session.getAttribute("enqId"), enqData);
			session.removeAttribute("enqId");
		} else {
			status = this.stdService.addEnq(enqData);
		}
		if (status == Account.ADDED || status == Account.ENQ_UPDATED)
			model.addAttribute("succMsg", status.getMessage());
		else
			model.addAttribute("errMsg", status.getMessage());
		this.viewInitialise(model);
		model.addAttribute("enqForm", new EnquiryForm());
		return "addenquiry";
	}

	@GetMapping("/enquiry/{id}")
	public String editEnquiry(@PathVariable("id") int id) {
		StdEnquiry enq = this.stdService.getEnq(id);
		session.setAttribute("enq", enq);
		session.setAttribute("enqId", id);
		return "redirect:/enquiry";
	}

	@GetMapping("/enquiries")
	public String viewEnquiry(Model model) {
		List<StdEnquiry> enqs = this.stdService.getEnq();
		this.viewInitialise(model);
		model.addAttribute("enqs", enqs);
		return "viewenquiry";
	}

	@GetMapping("/filter")
	public String filteredData(@RequestParam("courseName") String courseName, @RequestParam("status") String status,
			@RequestParam("mode") String mode, Model model) {
		EnquiryCriteria criteria = new EnquiryCriteria();
		criteria.setClassMode(mode);
		criteria.setCourseName(courseName);
		criteria.setEnquiryStatus(status);
		List<StdEnquiry> enqList = this.stdService.getEnq((int) session.getAttribute("userId"), criteria);
		model.addAttribute("enqList", enqList);
		return "enquiry";
	}
	@GetMapping("/delete/{id}")
	public String deleteEnquiry(@PathVariable("id")Integer id) {
//		this.stdService.delete(id);
		this.stdRepo.deleteById(id);
		return "redirect:/enquiries";
	}

	public void viewInitialise(Model model) {
		List<String> courses = this.stdService.getCourses();
		List<String> statuses = this.stdService.getEnqStatuses();
		model.addAttribute("courseList", courses);
		model.addAttribute("statusList", statuses);
	}

}
