package com.satya.util;

import org.apache.commons.lang3.RandomStringUtils;

public class PasswordUtil {
	public static String createPassword() {
		String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
		String pwd = RandomStringUtils.random(15, characters);
		return pwd;
	}
}
