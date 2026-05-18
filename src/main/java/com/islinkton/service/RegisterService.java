package com.islinkton.service;

import com.islinkton.dao.UserDAO;
import com.islinkton.utils.PasswordUtil;

public class RegisterService {
	
	UserDAO dao = new UserDAO();

    public void registerUser(String fullName, String username, 
    		String email, String password, String fileName) throws Exception {
    	
    	if (dao.getUserByEmail(email) != null) {
    	    throw new Exception("Email already exists");
    	}
    	
    	// for hashing password
    	password = PasswordUtil.hashPassword(password); 
    	
        dao.insertUser(fullName, username, email, password, fileName);
    }
}