package com.islinkton.service;

import java.util.ArrayList;
import java.util.List;

import com.islinkton.dao.UserDAO;
import com.islinkton.model.UserModel;

public class AdminService {

    public List<UserModel> getAllUsers() {

    	try {
            UserDAO dao = new UserDAO();
            return dao.getUsers();        //returns all users with role
        } 
    	catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();     //returns empty list instead of null
        }
    }
}