package com.islinkton.service;

import com.islinkton.dao.UserDAO;
import com.islinkton.model.User;
import com.islinkton.utils.PasswordUtil;

public class LoginService {

    public String login(String email, String password) {
        
    	// Basic input fields validation
    	if (email == null || email.trim().isEmpty()) {
            return "Email is required";
        }
        if (password == null || password.isEmpty()) {
            return "Password is required";
        }
        
        // Actual logic to fetch student data and do validation
        try {
        	
        	UserDAO userDAO = new UserDAO();
            User user = userDAO.getUserByEmail(email);

            // Check if user exists
            if (user == null) {
                return "User doesn't exist.";
            }

            // Verify the password using jBCrypt: BCrypt.checkpw(plain_text_password, hashed_password_from_db)
            if (PasswordUtil.checkPassword(password, user.getPassword())) {
                return "Success";
            } 
            else {
                return "Password is incorrect";
            }

        } catch (Exception e) {
        	e.printStackTrace();  
            System.out.println("Database Error Details: " + e.getMessage());
            e.printStackTrace();
            return "Error in Database: " + e.getMessage();
        }
    }
}