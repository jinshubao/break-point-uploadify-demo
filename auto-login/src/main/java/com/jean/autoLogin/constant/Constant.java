package com.jean.autoLogin.constant;

import java.util.HashSet;
import java.util.Set;

import com.jean.autoLogin.entity.User;

public class Constant {

	public static String USER_INFO_FILE_NAME = "AutoLogin.cfg";
	
	public static String USER_INFO_FILE_PATH = System.getProperty("user.dir") + "/";
	
	public static final Set<User> USERS = new HashSet<>();
}
