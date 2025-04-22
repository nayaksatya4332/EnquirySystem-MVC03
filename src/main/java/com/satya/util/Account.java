package com.satya.util;

public enum Account {
	ALREADY_UNLOCKED("Your Account is already unlocked.."), 
	WRONG_TEMPPASSWORD("Temporary Password is Wrong, Check your Email.."), 
	SUCCESS("Your Account has been Unlocked.."), 
	FAILED("Failed to Unlock Your Account.."), 
	NO_EMAIL_FOUND("You have given an Invalid email.."),
	MISMATCH("Password Mismatch,Retype your Passwords.."),
	WRONG_CREDENTIALS("Email or Password is Invalid.."),
	LOCKED("Your Account is Locked, Check Your Email to Unlock.."),
	LOGIN_SUCCESS("Log in Successfull.."),
	RECOVERY_SUCCESS("Password Recovered Successfully.."),
	ADDED("Enquiry Added Successfully.."),
	NOT_ADDED("All Fields are Required.."),
	NOT_LOGGEDIN("Log in to add Enquiry.."),
	ENQ_UPDATED("Enquiry Successfully Updated.."),
	UPDATE_FAILED("Failed To Update Enquiry.."),
	ENQ_DELETED("Enquiry Deleted..");
	

	private String message;

	Account(String msg) {
		this.message = msg;
	}

	public String getMessage() {
		return this.message;
	}
}
