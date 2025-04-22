package com.satya.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.satya.binding.LoginForm;
import com.satya.binding.SignupForm;
import com.satya.binding.UnlockForm;
import com.satya.service.UserService;
import com.satya.util.Account;

@Controller
public class UserController {
	@Autowired
	private UserService userService;
	@Autowired
	private HttpSession session;

	@GetMapping("/logout")
	public String logout() {
		this.session.invalidate();
		return "index";
	}

	@GetMapping("/login")
	public String login(Model model) {
		model.addAttribute("loginData", new LoginForm());
		return "login";
	}

	@PostMapping("/login")
	public String loginHandle(@ModelAttribute("loginData") LoginForm loginData, Model model) {
		Account status = this.userService.login(loginData);
		if (status == Account.LOGIN_SUCCESS) {
			return "redirect:/dashboard";
		}
		model.addAttribute("errMsg", status.getMessage());
		return "login";
	}

	@GetMapping("/register")
	public String signup(Model model) {
		model.addAttribute("signupData", new SignupForm());
		return "signup";
	}

	@PostMapping("/register")
	public String signupHandler(@ModelAttribute("signupData") SignupForm signup, Model model) {
		boolean status = this.userService.signup(signup);
		if (status) {
			model.addAttribute("success", "Registered Successfully,Check Your Email to unlock your Account.");
		} else {
			model.addAttribute("err", "An account with this email already Exists..");
		}
		return "signup";
	}

	@GetMapping("/unlock")
	public String unlock(@RequestParam("email") String email, Model model) {
		if (this.userService.checkAccountStatus(email) == true)
			return "invalid";

		UnlockForm unlockForm = new UnlockForm();
		unlockForm.setEmail(email);
		model.addAttribute("unlockData", unlockForm);
		return "unlock";
	}

	@PostMapping("/unlock")
	public String unlockAccount(@ModelAttribute("unlockData") UnlockForm unlockData, Model model) {

		Account status = this.userService.unlock(unlockData);
		if (status == Account.SUCCESS) {
			model.addAttribute("succMsg", status.getMessage());
		} else {
			model.addAttribute("errMsg", status.getMessage());
		}
		return "unlock";
	}

	@GetMapping("/forgot")
	public String recoverPage() {
		return "forgotpwd";
	}

	@PostMapping("/recover")
	public String recoverPwd(@RequestParam("email") String email, Model model) {
		Account status = this.userService.recover(email);
		if (status == Account.RECOVERY_SUCCESS)
			model.addAttribute("succMsg", status.getMessage() + ", Check Your Mail.");
		else
			model.addAttribute("errMsg", status.getMessage());

		return "forgotpwd";
	}
}
