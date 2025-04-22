package com.satya.service;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.satya.binding.LoginForm;
import com.satya.binding.SignupForm;
import com.satya.binding.UnlockForm;
import com.satya.entity.UserDetails;
import com.satya.repo.UserDetailsRepo;
import com.satya.util.Account;
import com.satya.util.EmailSender;
import com.satya.util.PasswordUtil;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserDetailsRepo usrRepo;
	@Autowired
	private EmailSender mailSender;
	
	@Autowired
	private HttpSession session;

	@Override
	public boolean checkAccountStatus(String email) {
		UserDetails user = this.usrRepo.findByEmail(email);
		if (user.getAccountStatus().equals("UNLOCKED"))
			return true;
		return false;
	}

	@Override
	public boolean signup(SignupForm signup) {

		if (this.usrRepo.findByEmail(signup.getEmail()) != null) {
			return false;
		}

		UserDetails user = new UserDetails();
		BeanUtils.copyProperties(signup, user);

		String tempPasswd = PasswordUtil.createPassword();
		String ip = "";
//		String port = env.getProperties().getProperty("local.server.port");
		user.setPwd(tempPasswd);
		user.setAccountStatus("LOCKED");
		this.usrRepo.save(user);
//		try {
//			ip = InetAddress.getLocalHost().getHostAddress();
//		} catch (UnknownHostException e) {
//			e.printStackTrace();
//		}
		String to = signup.getEmail();
		String subject = "Unlock Your Account";
		StringBuffer body = new StringBuffer("");
		body.append("<h1>Use Below Temporary Password to Unlock Your Account.</h1>");
		body.append("Temporary Password: " + tempPasswd);
		body.append("<br>");
		body.append(
				"<a href=\"http://localhost:9090/unlock?email=" + to + "\">Click here to unlock your Account..</a>");
		this.mailSender.sendMail(subject, body.toString(), to);

		return true;
	}

	@Override
	public Account unlock(UnlockForm unlockData) {

		if (!unlockData.getNewPwd().equals(unlockData.getConfirmPwd()))
			return Account.MISMATCH;

		UserDetails user = this.usrRepo.findByEmail(unlockData.getEmail());

		if (user == null)
			return Account.NO_EMAIL_FOUND;

		if (user.getAccountStatus().equals("UNLOCKED"))
			return Account.ALREADY_UNLOCKED;

		if (user.getPwd().equals(unlockData.getTempPwd())) {
			user.setPwd(unlockData.getNewPwd());
			user.setAccountStatus("UNLOCKED");
			this.usrRepo.save(user);
			return Account.SUCCESS;
		} else
			return Account.WRONG_TEMPPASSWORD;
	}

	@Override
	public Account login(LoginForm loginData) {
		UserDetails user = this.usrRepo.findByEmailAndPwd(loginData.getEmail(), loginData.getPwd());
		if (user == null) {
			return Account.WRONG_CREDENTIALS;
		}
		if (user.getAccountStatus().equals("LOCKED")) {
			return Account.LOCKED;
		}

		this.session.setAttribute("userId", user.getUserId());
		return Account.LOGIN_SUCCESS;
	}

	@Override
	public Account recover(String email) {
		UserDetails user = this.usrRepo.findByEmail(email);
		if (user == null)
			return Account.NO_EMAIL_FOUND;

		String subject = "Password Recovery";
		String body = "Password  ::  <b>" + user.getPwd() + "</b>";
		this.mailSender.sendMail(subject, body, user.getEmail());

		return Account.RECOVERY_SUCCESS;
	}

}
