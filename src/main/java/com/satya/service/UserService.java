package com.satya.service;

import com.satya.binding.LoginForm;
import com.satya.binding.SignupForm;
import com.satya.binding.UnlockForm;
import com.satya.util.Account;

public interface UserService {
	
	public boolean checkAccountStatus(String email);

	public boolean signup(SignupForm signup);
	
	public Account unlock(UnlockForm unlcok);
	
	public Account login(LoginForm login);
	
	public Account recover(String email);
}
